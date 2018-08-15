package com.opnay.todo.view

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.ActionBar
import com.opnay.todo.R
import com.opnay.todo.data.Category

fun ActionBar.setTitle(category: Category) {
    this.title = category.title.toUpperCase()
}

fun ActionBar.enableBack(en: Boolean, color: Int = Color.BLACK) {
    setDisplayHomeAsUpEnabled(en)
    setDisplayShowHomeEnabled(en)

    val drawable =
            ContextCompat.getDrawable(themedContext, R.drawable.ic_keyboard_arrow_left_black_24dp)!!

    DrawableCompat.setTint(drawable, color)
    setHomeAsUpIndicator(drawable)
}
