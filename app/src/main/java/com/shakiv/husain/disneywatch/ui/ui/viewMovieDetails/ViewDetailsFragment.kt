package com.shakiv.husain.disneywatch.ui.ui.viewMovieDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.shakiv.husain.disneywatch.DisneyApplication
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.data.model.details.MovieDetails
import com.shakiv.husain.disneywatch.data.model.image.Image
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.databinding.FragmentViewDetailsBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment
import com.shakiv.husain.disneywatch.ui.adapter.HorizontalImageAdapter
import com.shakiv.husain.disneywatch.ui.ui.home.MainViewModelFactory
import com.shakiv.husain.disneywatch.ui.ui.home.MovieViewModel
import com.shakiv.husain.disneywatch.util.AppConstants.ID
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentViewDetailsBinding

    private lateinit var viewModel: MovieViewModel

    private lateinit var horizontalAdapter: HorizontalImageAdapter

    @Inject
    lateinit var factory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        val id = arguments?.getString(ID) ?: ""

        horizontalAdapter = HorizontalImageAdapter(::onImageClick)

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


    override fun bindViews() {
        super.bindViews()


        binding.topViewPager.apply {
            adapter = horizontalAdapter
            clipToPadding = false
            clipChildren = false


        }


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
                        horizontalAdapter.submitList(imageList)
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
                        val castList= it.data?.cast?: emptyList()

                    }
                    is Resource.Loading -> {}
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
                tvVote.text = movieDetails.vote_average.toString()
                tvReleaseDate.text = movieDetails.release_date
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

        Log.d("onImageClick", "onImageClick: $image")
    }


    companion object {

        fun open(navController: NavController, id: String) {
            val id = getArgs(id)
            navigation(navController, id)
        }

        fun getArgs(id: String) = Bundle().apply {
            putSerializable(ID, id)
        }

        private fun navigation(controller: NavController, bundle: Bundle) {
            controller.navigate(R.id.action_global_viewDetails, bundle)
        }
    }

}

