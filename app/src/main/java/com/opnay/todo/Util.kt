package com.opnay.todo

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
