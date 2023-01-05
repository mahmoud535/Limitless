package com.evapharma.limitless.presentation.utils


import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.evapharma.limitless.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


fun showSnackBar(view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, length).setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).show()
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun View.enable(){
    isEnabled = true
}

fun View.disable(){
    isEnabled = false
}

fun loadImage (imageView:ImageView,url:String?){
    url?.let {
        Picasso.get().load(url).into(imageView)
    } ?: run {
        imageView.setImageResource(R.drawable.place_holder)
    }
}

