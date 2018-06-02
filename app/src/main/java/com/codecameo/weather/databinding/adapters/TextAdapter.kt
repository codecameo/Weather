package com.codecameo.weather.databinding.adapters

import android.databinding.BindingAdapter
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.format.DateFormat
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import com.codecameo.weather.EMPTY_STRING
import io.reactivex.annotations.Nullable
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("text_time")
fun setTextTime(textView: TextView, @Nullable time: String) {
    if (TextUtils.isEmpty(time)) return
    val time = SpannableString(time)
    time.setSpan(RelativeSizeSpan(2.5f), 0, time.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.text = time
}

@BindingAdapter("time", "time_zone_id", requireAll = false)
fun setTime(textView: TextView, @Nullable time: Long, @Nullable timeZoneId : String?) {
    if (time == 0L) return
    val millisecond = time * 1000L
    val timeZone = with(timeZoneId){
        if (TextUtils.isEmpty(timeZoneId)) TimeZone.getDefault() else TimeZone.getTimeZone(timeZoneId)
    }

    val sdf = SimpleDateFormat("hh:mma")
    sdf.timeZone = timeZone
    textView.text = sdf.format(Date(millisecond))
}