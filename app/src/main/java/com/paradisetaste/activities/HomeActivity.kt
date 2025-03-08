package com.paradisetaste.activities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.paradisetaste.R
import com.paradisetaste.databinding.ActivityHomeBinding
import com.paradisetaste.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding?=null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        _binding = ActivityHomeBinding.inflate(layoutInflater)

        val navigationHost = supportFragmentManager.findFragmentById(R.id.homeFragmentContainerView) as NavHostFragment
        val navigationController = navigationHost.navController

        binding.bottomNevigationView.setupWithNavController(navigationController)

        setContentView(binding.root)
    }

}