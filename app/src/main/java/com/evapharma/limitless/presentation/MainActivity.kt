package com.evapharma.limitless.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.ActivityMainBinding
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val host =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        setupWithNavController(binding.bottomNavBar, host.navController)

    }

    fun hideBottomNavigationView() {
        binding.bottomNavBar.hide()
    }

    fun showBottomNavigationView() {
        binding.bottomNavBar.show()
    }

}