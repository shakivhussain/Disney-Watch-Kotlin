package com.shakiv.husain.disneywatch.util

import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class DebounceTextWatch(
    @NonNull private val lifecycle: Lifecycle,
    private val timeMills: Long
) : TextWatcher {

    private var debounceDJob: Job? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        debouncedTextChange(s)
    }

    abstract fun afterTextDebounced(editable: Editable?)

    private fun debouncedTextChange(editable: Editable?) {

        debounceDJob?.cancel()
        debounceDJob = lifecycle.coroutineScope.launch {
            delay(timeMills)
            afterTextDebounced(editable)
        }

    }

}