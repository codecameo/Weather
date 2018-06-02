package com.codecameo.weather.databinding.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codecameo.weather.getBackgroundsRes
import com.codecameo.weather.getIconRes

@BindingAdapter("image_src")
fun ImageView.setImageIconSrc(code:String?){
    Glide.with(context)
            .load(getIconRes(code))
            .into(this)
}

@BindingAdapter("background")
fun ImageView.setImageBackgroundSrc(code:String?){
    Glide.with(context)
            .load(getBackgroundsRes(code))
            .centerCrop()
            .into(this)
}