package com.shakiv.husain.disneywatch.ui.ui.myFavourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shakiv.husain.disneywatch.databinding.LayoutMyFavouritesFragmentBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment

class MyFavouriteFragment : BaseFragment() {

    private lateinit var binding: LayoutMyFavouritesFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = LayoutMyFavouritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}