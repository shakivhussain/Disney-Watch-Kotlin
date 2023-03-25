package com.shakiv.husain.disneywatch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.shakiv.husain.disneywatch.util.BaseUi

open class BaseFragment : Fragment(), BaseUi{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}