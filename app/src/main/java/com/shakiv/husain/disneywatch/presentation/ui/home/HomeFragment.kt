package com.shakiv.husain.disneywatch.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shakiv.husain.disneywatch.R
import com.shakiv.husain.disneywatch.databinding.FragmentHomeBinding
import com.shakiv.husain.disneywatch.presentation.ui.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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


        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewDetailsFragment)
        }

    }

    override fun bindListeners() {
        super.bindListeners()
    }

    override fun bindObservers() {
        super.bindObservers()
    }
}