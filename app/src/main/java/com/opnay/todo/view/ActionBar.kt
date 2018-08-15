package com.opnay.todo.view

import android.graphics.Color
import android.support.v7.app.ActionBar
import com.opnay.todo.R
import com.opnay.todo.data.Category
import com.opnay.todo.getTintedDrawable

fun ActionBar.setTitle(category: Category) {
    this.title = category.title.toUpperCase()
}

fun ActionBar.enableBack(en: Boolean, color: Int = Color.BLACK) {
    setDisplayHomeAsUpEnabled(en)
    setDisplayShowHomeEnabled(en)

    setHomeAsUpIndicator(themedContext.getTintedDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp, color))
}
