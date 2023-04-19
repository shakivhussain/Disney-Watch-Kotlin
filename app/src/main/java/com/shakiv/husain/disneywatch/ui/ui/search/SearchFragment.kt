package com.shakiv.husain.disneywatch.ui.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import com.shakiv.husain.disneywatch.util.navigateToDestination
import com.shakiv.husain.disneywatch.util.setLinearLayoutManager
import com.shakiv.husain.disneywatch.util.toStringOrEmpty
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    lateinit var binding: LayoutSearchFragmentBinding

    @Inject lateinit var factory: MainViewModelFactory
    lateinit var movieViewModel: MovieViewModel
    lateinit var tvShowViewModel: TvShowViewModel
    lateinit var collectionViewModel: CollectionViewModel
    lateinit var moviesAdapter: MovieAdapter
    lateinit var tvShowAdapter: MovieAdapter
    lateinit var collectionsAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        initAdapters()
    }

    private fun searchQuery(query: String) {

        movieViewModel.searchMovies(query)
        tvShowViewModel.searchTvShow(query)
        collectionViewModel.searchCollections(query)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = LayoutSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.layoutSearch.editText.doOnDebouncedTextChange(lifecycle, 500) { editable ->

            val searchQuery = editable.toStringOrEmpty()
            searchQuery(searchQuery)

        }


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