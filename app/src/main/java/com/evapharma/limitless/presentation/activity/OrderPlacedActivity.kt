package com.evapharma.limitless.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.ActivityOrderPlacedBinding
import com.evapharma.limitless.presentation.MainActivity

class OrderPlacedActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderPlacedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPlacedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueShipping.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}