package com.shakiv.husain.disneywatch.ui.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakiv.husain.disneywatch.DisneyApplication
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.databinding.LayoutSearchFragmentBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment
import com.shakiv.husain.disneywatch.ui.adapter.MovieAdapter
import com.shakiv.husain.disneywatch.ui.ui.home.MainViewModelFactory
import com.shakiv.husain.disneywatch.ui.ui.home.MediaViewModel
import com.shakiv.husain.disneywatch.util.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    lateinit var binding: LayoutSearchFragmentBinding

    @Inject lateinit var factory: MainViewModelFactory
    lateinit var mediaViewModel: MediaViewModel
    lateinit var moviesAdapter: MovieAdapter
    lateinit var tvShowAdapter: MovieAdapter
    lateinit var collectionsAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        initAdapters()
    }

    private fun searchQuery(query: String) {

        mediaViewModel.searchMovies(query)
        mediaViewModel.searchTvShow(query)
        mediaViewModel.searchCollections(query)
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
            mediaViewModel.moviesPagingData.collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            mediaViewModel.tvShowsPagingData.collectLatest {
                tvShowAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            mediaViewModel.collectionPagingSource.collectLatest {
                collectionsAdapter.submitData(it)
            }
        }
    }

    private fun initAdapters() {
        moviesAdapter = MovieAdapter(onItemClicked = ::onItemClicked)
        tvShowAdapter = MovieAdapter(onItemClicked = ::onItemClicked)
        collectionsAdapter = MovieAdapter(onItemClicked = ::onItemClicked)
    }

    private fun onItemClicked(id: String) {
        navigateToDestination(movieId = id, actionId = R.id.action_global_viewDetails)
    }

    override fun initViewModels() {
        super.initViewModels()
        (this.activity?.application as DisneyApplication).appComponent.inject(this)
        mediaViewModel = ViewModelProvider(this, factory)[MediaViewModel::class.java]
    }
}