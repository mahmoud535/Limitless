package com.evapharma.limitless.presentation.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.IOException


// TODO Step 2: Create a custom object to create a common functions for Glide which can be used in whole application. You can also add the piece of code directly at the same place where you want to load the image with all the parameters.
// START
/**
 * A custom object to create a common functions for Glide which can be used in whole application.
 */
class GlideLoader(val context: Context) {


    fun loadProductPicture(image:Any,imageView:ImageView){
        try {
            // Load the user image in the ImageView.
            Glide
                    .with(context)
                    .load(Uri.parse(image.toString()))// URI of the image
                    .centerCrop()//Scale type of the image.
                    .into(imageView)// the view in which the image will be loaded.
        }catch (e:IOException){
            e.printStackTrace()
        }
    }



}