package com.shakiv.husain.disneywatch.ui.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.shakiv.husain.disneywatch.DisneyApplication
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.databinding.FragmentHomeBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment
import com.shakiv.husain.disneywatch.ui.adapter.HorizontalSliderAdapter
import com.shakiv.husain.disneywatch.ui.adapter.MovieAdapter
import com.shakiv.husain.disneywatch.ui.adapter.VerticalSliderAdapter
import com.shakiv.husain.disneywatch.ui.ui.viewMovieDetails.ViewDetailsFragment
import com.shakiv.husain.disneywatch.util.AppConstants.ID
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var factory: MainViewModelFactory
    lateinit var movieViewModel: MovieViewModel

    private lateinit var popularMoviesAdapter: MovieAdapter
    private lateinit var upcomingMovieAdapter: MovieAdapter
    private lateinit var horizontalAdapter: HorizontalSliderAdapter
    private lateinit var verticalSliderAdapter: VerticalSliderAdapter
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        initViewModels()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        bindListeners()
        bindObservers()

    }

    override fun bindViews() {
        super.bindViews()

        bindBannerSlider()
        bindPopularMovies()
        bindUpcomingMovies()
        bindNewMovies()

        binding.layoutPopularMovie.root.setOnClickListener {

            val id = "34g543ugh4vj4"
            val bundle = Bundle()
            bundle.putString(ID, id)

//            ViewDetailsFragment.open(findNavController(),"")
            findNavController().navigate(R.id.action_homeFragment_to_viewDetailsFragment, bundle)

        }

    }

    private fun bindNewMovies() {
        binding.viewPagerBottom.viewPager.apply {
            (getChildAt(0) as RecyclerView).clearOnChildAttachStateChangeListeners()
            adapter = horizontalAdapter

            binding.viewPager.setPageTransformer(getCompositePageTransformer())

            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if (handler != null) {
                        handler.removeCallbacks(update)
                        handler.postDelayed(update, 2000)
                    }
                }
            })
        }

    }


    private fun init() {
        handler = Handler()
        popularMoviesAdapter = MovieAdapter() {
        }
        upcomingMovieAdapter = MovieAdapter() {
        }
        horizontalAdapter = HorizontalSliderAdapter()
        verticalSliderAdapter = VerticalSliderAdapter()
    }

    private fun bindUpcomingMovies() {
        binding.layoutTrendingMovie.apply {
            recyclerView.isNestedScrollingEnabled = false
            ViewCompat.setNestedScrollingEnabled(recyclerView, false);

            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = upcomingMovieAdapter
            tvHeading.text = context?.resources?.getString(R.string.upcoming_movies)
        }
    }

    private fun bindPopularMovies() {
        binding.layoutPopularMovie.apply {
            recyclerView.isNestedScrollingEnabled = false
            ViewCompat.setNestedScrollingEnabled(recyclerView, false);

            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = popularMoviesAdapter
            tvHeading.text = context?.resources?.getString(R.string.popular_movies)
        }
    }

    private fun bindBannerSlider() {
        binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).clearOnChildAttachStateChangeListeners()
            adapter = verticalSliderAdapter

            binding.viewPager.setPageTransformer(getCompositePageTransformer())

            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if (handler != null) {
                        handler.removeCallbacks(update)
                        handler.postDelayed(update, 2000)
                    }
                }
            })
        }
    }

    override fun bindListeners() {
        super.bindListeners()
    }

    override fun bindObservers() {
        super.bindObservers()

        lifecycleScope.launch {
            movieViewModel.getTrendingMovies().collectLatest {
                verticalSliderAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            movieViewModel.getUpComingMovies().collectLatest {
                upcomingMovieAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            movieViewModel.getUpComingMovies().collectLatest {
                horizontalAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            movieViewModel.getPopularMovies().collectLatest {
                it?.let {
                    popularMoviesAdapter.submitData(it)
                }
            }
        }
    }

    var update = Runnable {
        var currentPage = binding.viewPager.currentItem
        var horizontalCurrentPage = binding.viewPagerBottom.viewPager.currentItem
        currentPage += 1
        horizontalCurrentPage += 1

        if (horizontalCurrentPage == horizontalAdapter.itemCount) {
            horizontalCurrentPage = 0
        }

        if (currentPage == verticalSliderAdapter.itemCount) {
            currentPage = 0
        }
        binding.viewPager.setCurrentItem(currentPage, true)
        binding.viewPagerBottom.viewPager.setCurrentItem(horizontalCurrentPage, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(update)
    }


    private fun getCompositePageTransformer(): CompositePageTransformer {
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page: View, position: Float ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }


        return compositePageTransformer

    }


    override fun initViewModels() {
        (this.activity?.application as DisneyApplication).appComponent.inject(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
    }
}