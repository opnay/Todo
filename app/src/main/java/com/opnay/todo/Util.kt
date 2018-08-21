package com.opnay.todo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat

class Util {
    companion object {
        const val KEY_CATEGORY = "CATEGORY"
        const val KEY_ITEM = "ITEM"
    }
}

fun Boolean.toLong(): Long = if (this) 1 else 0
fun Long.toBoolean(): Boolean = (this != 0L)

fun Boolean.toInt(): Int = if (this) 1 else 0
fun Int.toBoolean(): Boolean = (this != 0)

fun Boolean.toByte(): Byte = if (this) 1 else 0
fun Byte.toBoolean(): Boolean = (this != 0.toByte())

fun Context.getTintedDrawable(id: Int, color: Int = Color.BLACK): Drawable =
    ContextCompat.getDrawable(this, id)!!.apply { DrawableCompat.setTint(this, color) }

fun Context.getColorFromCompat(id: Int) = ContextCompat.getColor(this, id)
