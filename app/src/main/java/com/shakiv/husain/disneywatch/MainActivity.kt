package com.shakiv.husain.disneywatch

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.shakiv.husain.disneywatch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)
        hideActionBar()
        setBottomNavigation()
        bindViews()
    }

    private fun setBottomNavigation() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        bindings.bottomNavigation.setupWithNavController(navController)
    }

    private fun bindViews() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomNav()
                R.id.myFavouriteFragment -> showBottomNav()
                R.id.searchFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }

    }

    private fun hideBottomNav() {
        bindings.bottomNavigation.visibility = View.GONE
    }

    private fun showBottomNav() {
        bindings.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideActionBar() {
        (this as AppCompatActivity).supportActionBar?.hide()
    }

    private fun initViewModel() {
//        (application as DisneyApplication).appComponent.inject(this)
//        viewModel = ViewModelProvider(this, mainViewModelFactory)[MovieViewModel::class.java]
    }
}