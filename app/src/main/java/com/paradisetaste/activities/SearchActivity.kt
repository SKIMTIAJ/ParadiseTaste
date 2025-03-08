package com.paradisetaste.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.paradisetaste.R
import com.paradisetaste.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private var _binding: ActivitySearchBinding?=null
    private val binding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}