package com.evapharma.limitless.presentation.util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class SupportClass {
    companion object{
        fun startActivityWithFlags(context: Context, activity: Class<*>) {
            val intent = Intent(context, activity)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            ContextCompat.startActivity(context, intent, null)
        }

    }
}