package com.example.androidlessons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.androidlessons.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(),Navigator {

    private lateinit var binding: ActivityMainBinding

    private val fragmentListener: FragmentManager.FragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            update()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, createFragment())
                .commit()
        }
    }

    private fun createFragment(): RandomFragment {
        return RandomFragment.newInstance(generateUuid())
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun launchNext() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, createFragment())
            .commit()
    }

    override fun update() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (currentFragment is HasUuid) {
            binding.currentFragmentUuidTextView.text = currentFragment.getUuid()
        } else {
            binding.currentFragmentUuidTextView.text = ""
        }
        if (currentFragment is NumberListener) {
            currentFragment.onNewScreenNumber(1 + supportFragmentManager.backStackEntryCount)
        }
    }

    override fun generateUuid(): String = UUID.randomUUID().toString()
}