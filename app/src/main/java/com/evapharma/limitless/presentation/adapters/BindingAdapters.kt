package com.evapharma.limitless.presentation.adapters

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.evapharma.limitless.domain.model.ProductAttribute
import com.evapharma.limitless.presentation.utils.loadImage
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


@BindingAdapter("url")
fun bindImage(imageView: ImageView, url: String?) {
    loadImage(imageView, url)
}


@BindingAdapter("htmlText")
fun bindImage(textView: TextView, productAttribute: ProductAttribute) {
    val dirtyString = Html.fromHtml(productAttribute.values[0].valueRaw)
    val cleanString = dirtyString.replace(Regex("(?m)^[ \t]*\r?\n"), "");
    textView.text = cleanString
}


