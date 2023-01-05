package com.evapharma.limitless.presentation.splashScreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.evapharma.limitless.R
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.presentation.MainActivity
import com.evapharma.limitless.presentation.maps.MapsActivity
import com.evapharma.limitless.presentation.util.SupportClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private val mSplashScreenViewModel: SplashScreenViewModel by viewModels()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        coroutineScope.launch {
            checkLocationState()
        }
    }

    private suspend fun checkLocationState() {
        mSplashScreenViewModel.loading.observe(this) {
            if (it != null && it == false) {
                coroutineScope.launch(Dispatchers.IO) {
                    delay(2000)
                    if (mSplashScreenViewModel.getLocationState(this@SplashScreen))
                        SupportClass.startActivityWithFlags(
                            this@SplashScreen,
                            MainActivity::class.java
                        )
                    else {
                        SupportClass.startActivityWithFlags(
                            this@SplashScreen,
                            MapsActivity::class.java
                        )
                    }
                    finish()
                }

            }
        }


    }


}