package com.evapharma.limitless.presentation.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.evapharma.limitless.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)
    }
}