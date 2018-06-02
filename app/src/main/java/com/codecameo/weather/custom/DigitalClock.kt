package com.codecameo.weather.custom

import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.support.annotation.RequiresApi
import android.support.v7.widget.AppCompatTextView
import android.text.Spannable
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.widget.TextClock
import android.widget.TextView
import java.util.*

class DigitalClock: TextClock {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setText(text: CharSequence?, type: BufferType?) {
        val time = SpannableString(text)
        if (time.length>2){
            time.setSpan(RelativeSizeSpan(2.5f), 0, time.length - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        super.setText(time, type)
    }

}