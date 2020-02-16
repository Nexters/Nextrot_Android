package com.nextrot.troter.player.lyrics

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("app:htmlText")
fun setText(view: TextView, text: String) {
    view.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}