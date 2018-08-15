package com.opnay.todo.view

import android.support.v7.app.ActionBar
import com.opnay.todo.R
import com.opnay.todo.data.Category

fun ActionBar.setTitle(category: Category) {
    this.title = category.title.toUpperCase()
}

fun ActionBar.enableBack(en: Boolean) {
    setDisplayHomeAsUpEnabled(en)
    setDisplayShowHomeEnabled(en)
    setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp)
}
