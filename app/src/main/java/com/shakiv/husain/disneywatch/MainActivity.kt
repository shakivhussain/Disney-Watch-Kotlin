package com.shakiv.husain.disneywatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakiv.husain.disneywatch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindings.root)


    }

    private fun initViewModel() {
//        (application as DisneyApplication).appComponent.inject(this)
//        viewModel = ViewModelProvider(this, mainViewModelFactory)[MovieViewModel::class.java]
    }
}