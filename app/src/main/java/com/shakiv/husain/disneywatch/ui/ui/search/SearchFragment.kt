package com.shakiv.husain.disneywatch.ui.ui.search

import android.os.Bundle
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
import com.shakiv.husain.disneywatch.util.navigateToDestination
import com.shakiv.husain.disneywatch.util.setLinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    lateinit var binding: LayoutSearchFragmentBinding

    @Inject lateinit var factory: MainViewModelFactory
    lateinit var mediaViewModel: MediaViewModel
    lateinit var moviesAdapter: MovieAdapter
    lateinit var tvShowAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        initAdapters()
        searchQuery()

    }

    private fun searchQuery() {

        mediaViewModel.searchMovies("Shark")
        mediaViewModel.searchTvShow("the")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = LayoutSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    private fun initAdapters() {
        moviesAdapter = MovieAdapter(onItemClicked = ::onItemClicked)
        tvShowAdapter = MovieAdapter(onItemClicked = ::onItemClicked)
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