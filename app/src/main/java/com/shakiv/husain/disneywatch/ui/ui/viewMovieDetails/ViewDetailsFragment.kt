package com.shakiv.husain.disneywatch.ui.ui.viewMovieDetails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.shakiv.husain.disneywatch.DisneyApplication
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.data.model.MediaType
import com.shakiv.husain.disneywatch.data.model.details.movie.MovieDetails
import com.shakiv.husain.disneywatch.data.model.details.tvshow.TvShowDetails
import com.shakiv.husain.disneywatch.data.model.image.Image
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.databinding.FragmentViewDetailsBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment
import com.shakiv.husain.disneywatch.ui.adapter.CastAdapter
import com.shakiv.husain.disneywatch.ui.adapter.HorizontalImageAdapter
import com.shakiv.husain.disneywatch.ui.adapter.HorizontalSliderAdapter
import com.shakiv.husain.disneywatch.ui.adapter.HorizontalVideoAdapter
import com.shakiv.husain.disneywatch.ui.adapter.MovieAdapter
import com.shakiv.husain.disneywatch.ui.ui.home.CollectionViewModel
import com.shakiv.husain.disneywatch.ui.ui.home.MainViewModelFactory
import com.shakiv.husain.disneywatch.ui.ui.home.MovieViewModel
import com.shakiv.husain.disneywatch.ui.ui.home.TvShowViewModel
import com.shakiv.husain.disneywatch.util.AppConstants.ID
import com.shakiv.husain.disneywatch.util.AppConstants.MEDIA_TYPE
import com.shakiv.husain.disneywatch.util.AppConstants.TWO_SECONDS_IN_MILLIS
import com.shakiv.husain.disneywatch.util.AppConstants.ZERO
import com.shakiv.husain.disneywatch.util.ImageUtils
import com.shakiv.husain.disneywatch.util.convertToFullUrl
import com.shakiv.husain.disneywatch.util.getStringFromId
import com.shakiv.husain.disneywatch.util.logd
import com.shakiv.husain.disneywatch.util.setLinearLayoutManager
import com.shakiv.husain.disneywatch.util.toKNotation
import com.shakiv.husain.disneywatch.util.toStringOrEmpty
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentViewDetailsBinding

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var collectionViewModel: CollectionViewModel
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var horizontalImageAdapter: HorizontalImageAdapter
    private lateinit var horizontalSliderAdapter: HorizontalSliderAdapter
    private lateinit var castAdapter: CastAdapter
    private lateinit var recommendedMovieAdapter: MovieAdapter
    private lateinit var videoAdapter: HorizontalVideoAdapter
    private var movieDetails: MovieDetails? = null
    private lateinit var autoScrollHandler: Handler

    @Inject
    lateinit var factory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arguments = requireArguments()
        val id = arguments.getString(ID).toStringOrEmpty()
        val type = arguments.getSerializable(MEDIA_TYPE) as? MediaType

        autoScrollHandler = Handler(Looper.getMainLooper())

        initViewModels()
        initAdapter()

        fetchMediaDetails(id, type)
    }

    val updateRunnable = Runnable {

        var photosSliderCurrentPage = binding.topViewPager.currentItem
        photosSliderCurrentPage += 1

        if (photosSliderCurrentPage == horizontalImageAdapter.itemCount) {
            photosSliderCurrentPage = ZERO
        }

        binding.topViewPager.setCurrentItem(photosSliderCurrentPage, true)
    }


    private fun fetchMediaDetails(id: String, type: MediaType?) {

        when (type) {
            MediaType.MOVIE -> {
                fetchMovieDetails(id)
            }

            MediaType.TV -> {
                fetchTvDetails(id)
            }

            MediaType.COLLECTION -> {
                fetchCollectionDetails(id)
            }

            else -> {
            }
        }

    }

    private fun fetchCollectionDetails(id: String) {
        lifecycleScope.launch {
            collectionViewModel.getCollectionDetails(id).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        movieDetails = it.data
                        bindMovieDetailsData(movieDetails)
                    }

                    is Resource.Loading -> {
                    }

                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            collectionViewModel.getCollectionImages(id).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val backdrops = it.data?.backdrops ?: emptyList()
                        horizontalImageAdapter.submitList(backdrops)
                    }

                    is Resource.Loading -> {}
                    is Resource.Failure -> {}
                }
            }
        }

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
        horizontalSliderAdapter = HorizontalSliderAdapter(onItemClick = ::onImageClick)
        castAdapter = CastAdapter()
        recommendedMovieAdapter = MovieAdapter(onItemClicked = ::onItemClicked)
        videoAdapter = HorizontalVideoAdapter(lifecycle)
    }

    private fun onItemClicked(id: String) {

        val bundle = Bundle()
        bundle.putString(ID, id)
        findNavController().navigate(R.id.viewDetailsFragment, bundle)
    }


    override fun bindViews() {
        super.bindViews()
        val castTitle = getStringFromId(R.string.cast)
        val recommendedForYou = getStringFromId(R.string.recommended_for_you)
        val videos = getStringFromId(R.string.videos)

        bindMovieDetailsData(movieDetails)

        binding.layoutHeader.apply {
            buttonBack.isVisible = true
        }

        binding.topViewPager.apply {
            adapter = horizontalImageAdapter
            clipToPadding = false
            clipChildren = false

            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    autoScrollHandler.removeCallbacks(updateRunnable)
                    autoScrollHandler.postDelayed(updateRunnable, TWO_SECONDS_IN_MILLIS)
                }
            })
        }

        binding.recommendedLayout.apply {
            recyclerView.setLinearLayoutManager(context ?: return, LinearLayoutManager.HORIZONTAL)
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
            recyclerView.setLinearLayoutManager(context ?: return, LinearLayoutManager.HORIZONTAL)
        }
    }


    private fun fetchMovieDetails(id: String) {

        lifecycleScope.launch {
            movieViewModel.getMovieDetails(id).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        movieDetails = it.data
                        bindMovieDetailsData(movieDetails)
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Failure -> {
                    }
                }
            }
        }

        lifecycleScope.launch {
            movieViewModel.getMovieImages(id).collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val imageList = it.data?.backdrops ?: emptyList()
                        horizontalImageAdapter.submitList(imageList)
                    }

                    is Resource.Loading -> {}
                    is Resource.Failure -> {}
                }
            }
        }

        lifecycleScope.launch {
            movieViewModel.getCasts(movieId = id).collectLatest {

                when (it) {
                    is Resource.Success -> {
                        val castList = it.data?.cast ?: emptyList()
                        castAdapter.submitList(castList)
                        binding.castLayout.root.isVisible = castList.isNotEmpty()
                    }

                    is Resource.Loading -> {}
                    is Resource.Failure -> {}
                }
            }
        }

        lifecycleScope.launch {
            movieViewModel.getRecommended(id).collectLatest {
                recommendedMovieAdapter.submitData(it)
            }
        }


        lifecycleScope.launch {
            movieViewModel.getMoviesPreview(id).collectLatest {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val previewResponse = it.data?.previewList ?: emptyList()
                        binding.viewPagerBottom.root.isVisible = previewResponse.isNotEmpty()
                        videoAdapter.submitList(previewResponse)

                    }

                    is Resource.Failure -> {}
                }
            }
        }


    }

    private fun fetchTvDetails(id: String) {
        tvShowViewModel.getTvShowDetails(id)
        tvShowViewModel.getCredits(id)
        tvShowViewModel.getVideos(id)

        lifecycleScope.launch {
            tvShowViewModel.tvShowDetails.collectLatest {

                when (it) {
                    is Resource.Success -> {
                        val tvShowDetails = it.data
                        bindMovieDetailsData(tvShowDetails)
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Failure -> {
                    }

                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            tvShowViewModel.tvShowCredit.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val tvShowCredits = it.data?.cast ?: emptyList()
                        castAdapter.submitList(tvShowCredits)
                        binding.castLayout.root.isVisible = tvShowCredits.isNotEmpty()
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Failure -> {
                    }

                    else -> {}
                }
            }

        }

        lifecycleScope.launch {
            tvShowViewModel.tvShowVideos.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val tvShowVideos = it.data?.previewList ?: emptyList()
                        videoAdapter.submitList(tvShowVideos)
                        binding.viewPagerBottom.root.isVisible = tvShowVideos.isNotEmpty()
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Failure -> {
                    }

                    else -> {}
                }
            }

        }

        lifecycleScope.launch {
            tvShowViewModel.getRecommendedTvShows(id).collectLatest {
                recommendedMovieAdapter.submitData(it)
            }
        }
    }


    private fun bindMovieDetailsData(movieDetails: MovieDetails?) {
        if (movieDetails == null) {
            return
        }
        binding.apply {
            movieDetails.let { movieDetails: MovieDetails ->
                tvMovieName.text = movieDetails.name.orEmpty()
                val collectionDetails = movieDetails.parts?.getOrNull(0)
                collectionDetails?.let { collection ->
                    tvTitle.text = collection.title.orEmpty()
                    tvRelease.text = collection.mediaType.orEmpty()
                    tvReleaseDate.text = collection.releaseDate.orEmpty()
                    tvRevenue.text = "---"
                    tvMovieDesc.text = collection.overview.orEmpty()
                    tvStatusTitle.text = resources.getString(R.string.vote_average)
                    tvStatus.text = collection.voteAverage.toStringOrEmpty()
                    tvVote.text = collection.voteCount?.toKNotation().orEmpty()
                }

                ImageUtils.setImage(
                    movieDetails.poster_path?.convertToFullUrl().orEmpty(), layoutPoster.imageView
                )

            }
        }
    }

    private fun bindMovieDetailsData(tvShowDetails: TvShowDetails?) {
        if (tvShowDetails == null) {
            return
        }

        binding.apply {
            tvShowDetails.let { tvshowDetails: TvShowDetails ->
                tvMovieDesc.text = tvshowDetails.overview.orEmpty()
                tvStatus.text = tvshowDetails.status.orEmpty()
                tvVote.text = tvshowDetails.vote_count?.toKNotation().orEmpty()
                tvReleaseDate.text = tvshowDetails.first_air_date.orEmpty()

                tvTitle.text = tvshowDetails.name.orEmpty()
                tvRelease.text = tvshowDetails.status.orEmpty()
                tvReleaseDate.text = tvshowDetails.first_air_date.orEmpty()

                tvRevenueTitle.text = resources.getString(R.string.type)
                tvRevenue.text = tvshowDetails.type.orEmpty()

                ImageUtils.setImage(
                    tvshowDetails.poster_path?.convertToFullUrl().orEmpty(), layoutPoster.imageView
                )

                val listOfBackDrop = listOf(
                    Image(file_path = tvshowDetails.backdrop_path.orEmpty())
                )
                horizontalImageAdapter.submitList(listOfBackDrop)
            }
        }


    }

    override fun bindListeners() {
        super.bindListeners()
        binding.layoutHeader.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun bindObservers() {
        super.bindObservers()

        lifecycleScope.launch {
            recommendedMovieAdapter.loadStateFlow.collectLatest {
                binding.recommendedLayout.root.isVisible =
                    it.refresh is LoadState.NotLoading && recommendedMovieAdapter.itemCount > 1
            }
        }

    }

    override fun initViewModels() {
        super.initViewModels()
        (activity?.application as DisneyApplication).appComponent.inject(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        collectionViewModel = ViewModelProvider(this, factory)[CollectionViewModel::class.java]
        tvShowViewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

    }

    private fun onImageClick(image: String) {

    }

}

