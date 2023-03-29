package com.shakiv.husain.disneywatch.ui.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.shakiv.husain.disneywatch.DisneyApplication
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.databinding.FragmentHomeBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment
import com.shakiv.husain.disneywatch.ui.adapter.MovieAdapter
import com.shakiv.husain.disneywatch.ui.adapter.SliderAdapter
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
    private lateinit var trendingMovieAdapter: MovieAdapter
    private lateinit var sliderAdapter: SliderAdapter
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

        binding.root.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_viewDetailsFragment)
        }

    }

    private fun bindUpcomingMovies() {

        binding.layoutUpcomingMovie.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = trendingMovieAdapter
            tvHeading.text = context?.resources?.getString(R.string.upcoming_movies)
        }
    }

    private fun bindPopularMovies() {
        binding.layoutPopularMovie.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = popularMoviesAdapter
            tvHeading.text = context?.resources?.getString(R.string.popular_movies)
        }
    }

    private fun bindBannerSlider() {
        binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).clearOnChildAttachStateChangeListeners()
            adapter = sliderAdapter
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page: View, position: Float ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
            binding.viewPager.setPageTransformer(compositePageTransformer)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(update)
                    handler.postDelayed(update, 2000)
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
                trendingMovieAdapter.submitData(it)
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
        currentPage += 1
        if (currentPage == sliderAdapter.itemCount) {
            currentPage = 0
        }
        binding.viewPager.setCurrentItem(currentPage, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(update)
    }

    private fun init() {
        handler = Handler()
        popularMoviesAdapter = MovieAdapter()
        trendingMovieAdapter = MovieAdapter()
        sliderAdapter = SliderAdapter()
    }

    override fun initViewModels() {
        (this.activity?.application as DisneyApplication).appComponent.inject(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
    }
}