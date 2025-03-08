package com.paradisetaste.utils

import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.paradisetaste.R
import retrofit2.http.Url

object GlideUtils {

    fun loadImage(
        imageView: ImageView,
        imageUrl: String?,
        placeholder: Int = R.drawable.barger_image,
        error: Int = R.drawable.ic_error_outline
    ) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(placeholder)
            .error(error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    //Method to load Image with Resizing
    fun loadResizedImage(
        imageView: ImageView,
        imageUrl: String?,
        width: Int,
        height: Int,
        placeholder: Int = R.drawable.barger_image,
        error: Int = R.drawable.ic_error_outline
    ) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(placeholder)
            .error(error)
            .override(width, height)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    //Method for circular Image
    fun loadCircularImage(
        imageView: ImageView,
        imageUrl: String?,
        placeholder: Int = R.drawable.barger_image
    ) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(placeholder)
            .circleCrop()
            .into(imageView)
    }

}