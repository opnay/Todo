package com.opnay.todo.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.opnay.todo.R

class ProgressText(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    val textView: TextView = TextView(context, attrs)
    val progressBar: ProgressBar =
            ProgressBar(context, attrs, android.R.attr.progressBarStyleHorizontal).apply {
                // Percentage
                max = 100
            }

    var progress: Int
        get() = progressBar.progress
        set(v) {
            progressBar.progress = v
            textView.text = resources.getString(R.string.cat_fragment_complete, v)
        }

    init {
        orientation = LinearLayout.VERTICAL
        addView(textView)
        addView(progressBar)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        textView.apply {
            // getDimension will return as px. so, need to be TypedValue.COMPLEX_UNIT_PX
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.progress_text_size))
            setTextColor(ContextCompat.getColorStateList(context, R.color.text_black_secondary))
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        textView.isEnabled = enabled
        progressBar.isEnabled = enabled
    }

}