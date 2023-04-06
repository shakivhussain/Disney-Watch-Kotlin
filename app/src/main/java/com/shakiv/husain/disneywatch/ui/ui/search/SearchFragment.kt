package com.shakiv.husain.disneywatch.ui.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shakiv.husain.disneywatch.databinding.LayoutSearchFragmentBinding
import com.shakiv.husain.disneywatch.ui.BaseFragment

class SearchFragment : BaseFragment() {

    lateinit var binding: LayoutSearchFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = LayoutSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}