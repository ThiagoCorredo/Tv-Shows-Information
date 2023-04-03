package com.tcorredo.tvshow.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tcorredo.tvshow.R
import com.tcorredo.tvshow.databinding.ActivityMainBinding
import com.tcorredo.tvshow.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.bottomNavigation.apply {
            setupWithNavController(findNavController(R.id.fragment_container))
        }
    }
}