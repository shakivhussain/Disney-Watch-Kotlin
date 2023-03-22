package com.shakiv.husain.disneywatch.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.shakiv.husain.disneywatch.util.BaseUi

open class BaseFragment : Fragment(), BaseUi{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}