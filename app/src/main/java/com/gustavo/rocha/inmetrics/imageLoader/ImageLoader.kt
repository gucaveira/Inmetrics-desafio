package com.gustavo.rocha.inmetrics.imageLoader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.gustavo.rocha.inmetrics.R

interface ImageLoader {
    fun load(
        imageView: ImageView, imageUrl: String,
        @DrawableRes placerHolder: Int = R.drawable.ic_img_placeholder,
        @DrawableRes fallback: Int = R.drawable.ic_img_loading_error,
    )
}
