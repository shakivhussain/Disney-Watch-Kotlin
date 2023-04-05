package com.shakiv.husain.disneywatch.ui.ui.viewMovieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shakiv.husain.disneywatch.DisneyApplication
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.image.Image
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.databinding.FragmentViewDetailsBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment
import com.shakiv.husain.disneywatch.ui.adapter.*
import com.shakiv.husain.disneywatch.ui.ui.home.MainViewModelFactory
import com.shakiv.husain.disneywatch.ui.ui.home.MovieViewModel
import com.shakiv.husain.disneywatch.util.*
import com.shakiv.husain.disneywatch.util.AppConstants.ID
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentViewDetailsBinding

    private lateinit var viewModel: MovieViewModel
    private lateinit var horizontalImageAdapter: HorizontalImageAdapter
    private lateinit var horizontalSliderAdapter: HorizontalSliderAdapter
    private lateinit var castAdapter: CastAdapter
    private lateinit var recommendedMovieAdapter: MovieAdapter
    private lateinit var videoAdapter : HorizontalVideoAdapter

    @Inject
    lateinit var factory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = arguments?.getString(ID) ?: ""

        initViewModels()
        initAdapter()
        fetchMovieDetails(id)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        bindListeners()
        bindObservers()
    }

    private fun initAdapter() {
        horizontalImageAdapter = HorizontalImageAdapter(::onImageClick)
        horizontalSliderAdapter = HorizontalSliderAdapter()
        castAdapter = CastAdapter()
        recommendedMovieAdapter = MovieAdapter(onItemClicked = ::onItemClicked)
        videoAdapter = HorizontalVideoAdapter(lifecycle)
    }

    private fun onItemClicked(id: String) {
    }


    override fun bindViews() {
        super.bindViews()
        val castTitle = getStringFromId(R.string.cast)
        val recommendedForYou = getStringFromId(R.string.recommended_for_you)
        val videos = getStringFromId(R.string.videos)




        binding.topViewPager.apply {
            adapter = horizontalImageAdapter
            clipToPadding = false
            clipChildren = false
        }

        binding.recommendedLayout.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = recommendedMovieAdapter
            tvHeading.isVisible = recommendedForYou.isNotEmpty()
            tvHeading.text = recommendedForYou
        }

        binding.viewPagerBottom.viewPager.apply {
            (getChildAt(0) as RecyclerView).clearOnChildAttachStateChangeListeners()

            adapter = videoAdapter
            clipToPadding = false
            clipChildren = false

        }

        binding.viewPagerBottom.tvHeading.apply {
            isVisible = videos.isNotEmpty()
            text = videos
        }


        binding.castLayout.apply {
            tvHeading.text = castTitle
            recyclerView.adapter = castAdapter
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

//        binding.viewPagerBottom.apply {
//            viewPager.adapter= videoAdapter
//            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
//        }

    }

    private fun fetchMovieDetails(id: String) {

        lifecycleScope.launch {
            viewModel.getMovieDetails(id).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        bindMovieDetailsData(it.data ?: return@collectLatest)
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Failure -> {
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getMovieImages(id).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val imageList = it.data?.images ?: emptyList()
                        horizontalImageAdapter.submitList(imageList)
                    }
                    is Resource.Loading -> {}
                    is Resource.Failure -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getCasts(movieId = id).collectLatest {

                when (it) {
                    is Resource.Success -> {
                        val castList = it.data?.cast ?: emptyList()
                        castAdapter.submitList(castList)
                    }
                    is Resource.Loading -> {}
                    is Resource.Failure -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getRecommended(id).collectLatest {
                recommendedMovieAdapter.submitData(it)

            }
        }


        lifecycleScope.launch {
            viewModel.getMoviesPreview(id).collectLatest {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val previewResponse = it.data?.previewList ?: emptyList()
                        logd(previewResponse.toString())

                        videoAdapter.submitList(previewResponse)

                    }
                    is Resource.Failure -> {}
                }
            }
        }
    }

    private fun bindMovieDetailsData(movieDetails: MovieDetails) {
        binding.apply {
            movieDetails.let { movieDetails: MovieDetails ->
                tvMovieName.text = movieDetails.title
                tvMovieDesc.text = movieDetails.overview
                tvStatus.text = movieDetails.status
                tvVote.text = movieDetails.vote_count.toKNotation()
                tvReleaseDate.text = movieDetails.release_date

                tvTitle.text = movieDetails.title
                tvRelease.text = movieDetails.status
                tvReleaseDate.text = movieDetails.release_date
                tvRevenue.text = movieDetails.revenue?.toKNotation()
                ImageUtils.setImage(movieDetails.poster_path.convertToFullUrl(), layoutPoster.imageView)

            }
        }
    }

    override fun bindListeners() {
        super.bindListeners()
    }

    override fun bindObservers() {
        super.bindObservers()
    }

    override fun initViewModels() {
        super.initViewModels()
        (activity?.application as DisneyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

    }

    private fun onImageClick(image: Image) {
    }

}

