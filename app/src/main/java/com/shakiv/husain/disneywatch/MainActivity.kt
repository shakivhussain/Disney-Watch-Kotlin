package com.shakiv.husain.disneywatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.shakiv.husain.disneywatch.data.network.Resource
import com.shakiv.husain.disneywatch.databinding.ActivityMainBinding
import com.shakiv.husain.disneywatch.presentation.ui.home.MainViewModelFactory
import com.shakiv.husain.disneywatch.presentation.ui.home.MovieViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        initViewModel()
        viewModel.getTopRatedMovies()

        lifecycleScope.launch {
            viewModel.topRatedMovies.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data
                    }

                    is Resource.Failure -> {
                    }

                    is Resource.Failure -> {
                    }
                    is Resource.Loading -> {
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        (application as DisneyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MovieViewModel::class.java]
    }
}