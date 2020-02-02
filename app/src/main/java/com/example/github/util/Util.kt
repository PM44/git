package com.example.github.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.github.R

fun getProcessDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }


}

fun ImageView.loadImageView(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_error_outline)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .apply(RequestOptions.circleCropTransform())
        .into(this)

}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImageView(url, getProcessDrawable(view.context))
}