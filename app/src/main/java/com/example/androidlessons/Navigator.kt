package com.example.androidlessons

import androidx.fragment.app.Fragment

interface Navigator {
    fun launchNext()
    fun generateUuid():String
    fun update()
}
fun Fragment.navigator(): Navigator = requireActivity() as Navigator

