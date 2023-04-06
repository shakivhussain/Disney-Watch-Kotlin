package com.shakiv.husain.disneywatch.ui.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakiv.husain.disneywatch.DisneyApplication
import com.shakiv.husain.disneywatch.databinding.LayoutSearchFragmentBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment
import com.shakiv.husain.disneywatch.ui.adapter.MovieAdapter
import com.shakiv.husain.disneywatch.ui.ui.home.MainViewModelFactory
import com.shakiv.husain.disneywatch.ui.ui.home.MovieViewModel
import com.shakiv.husain.disneywatch.util.setLinearLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    lateinit var binding: LayoutSearchFragmentBinding

    @Inject
    lateinit var factory: MainViewModelFactory
    lateinit var movieViewModel: MovieViewModel
    lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        initAdapters()
        movieViewModel.searchMovies("Shark")

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
            recyclerView.setLinearLayout(context ?: return, LinearLayoutManager.HORIZONTAL)
            tvHeading.text = "Movies"
        }
    }

    override fun bindObservers() {
        super.bindObservers()


        lifecycleScope.launch {
            movieViewModel.moviesPagingData.collectLatest {
                moviesAdapter.submitData(it)
            }
        }
    }

    private fun initAdapters() {
        moviesAdapter = MovieAdapter(onItemClicked = ::onItemClicked)
    }

    private fun onItemClicked(id: String) {

    }

    override fun initViewModels() {
        super.initViewModels()
        (this.activity?.application as DisneyApplication).appComponent.inject(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
    }
}