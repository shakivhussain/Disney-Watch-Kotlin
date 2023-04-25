package com.shakiv.husain.disneywatch.ui.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakiv.husain.disneywatch.DisneyApplication
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.data.model.MediaType
import com.shakiv.husain.disneywatch.databinding.LayoutSearchFragmentBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment
import com.shakiv.husain.disneywatch.ui.adapter.MovieAdapter
import com.shakiv.husain.disneywatch.ui.ui.home.CollectionViewModel
import com.shakiv.husain.disneywatch.ui.ui.home.MainViewModelFactory
import com.shakiv.husain.disneywatch.ui.ui.home.MovieViewModel
import com.shakiv.husain.disneywatch.ui.ui.home.TvShowViewModel
import com.shakiv.husain.disneywatch.util.AppConstants.ID
import com.shakiv.husain.disneywatch.util.AppConstants.MEDIA_TYPE
import com.shakiv.husain.disneywatch.util.doOnDebouncedTextChange
import com.shakiv.husain.disneywatch.util.getCurrentVisiblePosition
import com.shakiv.husain.disneywatch.util.logd
import com.shakiv.husain.disneywatch.util.navigateToDestination
import com.shakiv.husain.disneywatch.util.setLinearLayoutManager
import com.shakiv.husain.disneywatch.util.toStringOrEmpty
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    lateinit var binding: LayoutSearchFragmentBinding
    private var rootView: ConstraintLayout? = null

    @Inject lateinit var factory: MainViewModelFactory
    lateinit var movieViewModel: MovieViewModel
    private lateinit var tvShowViewModel: TvShowViewModel
    lateinit var collectionViewModel: CollectionViewModel
    lateinit var moviesAdapter: MovieAdapter
    lateinit var tvShowAdapter: MovieAdapter
    lateinit var collectionsAdapter: MovieAdapter
    private var currentPositionOfMovieRecyclerView: Int = 0
    private var currentPositionOfCollectionRecyclerView: Int = 0
    private var currentPositionOfTvShowRecyclerView: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(400)
            binding.layoutSearch.editText.doOnDebouncedTextChange(lifecycle, 500) { editable ->
                val searchQuery = editable.toStringOrEmpty()
                logd("searchQuery : $searchQuery", "CheckLog")
                searchQuery(searchQuery)
            }
        }
        initViewModels()
        initAdapters()
    }


    override fun onPause() {
        super.onPause()
        currentPositionOfMovieRecyclerView =
            binding.layoutMovies.recyclerView.getCurrentVisiblePosition()
        currentPositionOfCollectionRecyclerView =
            binding.layoutCollections.recyclerView.getCurrentVisiblePosition()
        currentPositionOfTvShowRecyclerView =
            binding.layoutTvShow.recyclerView.getCurrentVisiblePosition()
    }


    override fun onResume() {
        super.onResume()
        binding.layoutMovies.recyclerView.scrollToPosition(currentPositionOfMovieRecyclerView)
        binding.layoutCollections.recyclerView.scrollToPosition(
            currentPositionOfCollectionRecyclerView
        )
        binding.layoutTvShow.recyclerView.scrollToPosition(currentPositionOfTvShowRecyclerView)
    }

    private fun searchQuery(query: String) {

        movieViewModel.searchMovies(query)
        tvShowViewModel.searchTvShow(query)
        collectionViewModel.searchCollections(query)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            binding = LayoutSearchFragmentBinding.inflate(inflater, container, false)
            rootView = binding.root
        }
        return rootView as ConstraintLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindListeners()
        bindObservers()
        bindViews()
    }

    override fun bindViews() {
        super.bindViews()


        binding.layoutMovies.apply {
            recyclerView.adapter = moviesAdapter
            recyclerView.setLinearLayoutManager(context ?: return, LinearLayoutManager.HORIZONTAL)
            tvHeading.text = "Movies"
        }

        binding.layoutTvShow.apply {
            recyclerView.adapter = tvShowAdapter
            recyclerView.setLinearLayoutManager(context ?: return, LinearLayoutManager.HORIZONTAL)
            recyclerView.setHasFixedSize(true)
            tvHeading.text = "Tv Shows"
        }


        binding.layoutCollections.apply {

            recyclerView.adapter = collectionsAdapter
            recyclerView.setLinearLayoutManager(context ?: return, LinearLayoutManager.HORIZONTAL)
            recyclerView.setHasFixedSize(true)
            tvHeading.text = "Collections"
        }
    }

    override fun bindListeners() {
        super.bindListeners()

    }

    override fun bindObservers() {
        super.bindObservers()

        lifecycleScope.launch {
            movieViewModel.moviesPagingData.collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            tvShowViewModel.tvShowsPagingData.collectLatest {
                tvShowAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            collectionViewModel.collectionPagingSource.collectLatest {
                collectionsAdapter.submitData(it)
            }
        }


        lifecycleScope.launch {

            moviesAdapter.loadStateFlow.collectLatest {

                when (it.refresh) {
                    is LoadState.Loading -> {}
                    is LoadState.NotLoading -> {
                        val isEmptyAdapter = (moviesAdapter.itemCount < 1)
                        val needToShowRecyclerData = !isEmptyAdapter
                        binding.layoutMovies.root.isVisible = needToShowRecyclerData
                        binding.layoutTvShow.root.isVisible = needToShowRecyclerData
                        binding.layoutCollections.root.isVisible = needToShowRecyclerData
                        binding.iVEmptyBackground.isVisible = isEmptyAdapter
                    }

                    else -> {}
                }
            }
        }
    }

    private fun initAdapters() {
        moviesAdapter = MovieAdapter(onItemClicked = { id -> onItemClicked(id, MediaType.MOVIE) })
        tvShowAdapter = MovieAdapter(onItemClicked = { id -> onItemClicked(id, MediaType.TV) })
        collectionsAdapter =
            MovieAdapter(onItemClicked = { id -> onItemClicked(id, MediaType.COLLECTION) })
    }

    private fun onItemClicked(id: String, type: MediaType) {
        val bundle = bundleOf(
            ID to id,
            MEDIA_TYPE to type
        )
        navigateToDestination(bundle, actionId = R.id.action_global_viewDetails)
    }

    override fun initViewModels() {
        super.initViewModels()
        (this.activity?.application as DisneyApplication).appComponent.inject(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        collectionViewModel = ViewModelProvider(this, factory)[CollectionViewModel::class.java]
        tvShowViewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
    }
}