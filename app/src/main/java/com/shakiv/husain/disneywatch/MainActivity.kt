package com.shakiv.husain.disneywatch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.shakiv.husain.disneywatch.databinding.ActivityMainBinding
import com.shakiv.husain.disneywatch.presentation.ui.home.MainViewModelFactory
import com.shakiv.husain.disneywatch.presentation.ui.home.MovieViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding
    private var navController: NavController? = null

    @Inject
    lateinit var mainViewModelFactory : MainViewModelFactory


    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        (application as DisneyApplication).appComponent.inject(this)

        viewModel = ViewModelProvider(this,  mainViewModelFactory)[MovieViewModel::class.java]


        lifecycleScope.launch {
            delay(1000)
            viewModel.getTopRatedMovies().collectLatest {

                Log.d("Response","Reponse : ${it}")

            }
        }

//        viewModel.productsLiveData.observe(this){
//
//
//            Log.d("Response","Reponse : ${it}")
//        }

    }
}