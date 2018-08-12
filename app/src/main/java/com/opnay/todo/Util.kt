package com.opnay.todo

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import java.io.Serializable

class Util {
    companion object {
        fun startActivity(ctx: Context, cls: Class<*>) { startActivity(ctx, cls, null) }
        fun startActivity(context: Context, cls: Class<*>, extra: HashMap<String, *>?) {
            Intent(context, cls).run {
                extra?.forEach { k, d ->
                    if (d is Parcelable)
                        this.putExtra(k, d)
                    else
                        this.putExtra(k, d as Serializable)
                }
                context.startActivity(this)
            }
        }
    }
}

fun Boolean.toLong(): Long = if (this) 1 else 0
fun Long.toBoolean(): Boolean = (this != 0L)

fun Boolean.toInt(): Int = if (this) 1 else 0
fun Int.toBoolean(): Boolean = (this != 0)

fun Boolean.toByte(): Byte = if (this) 1 else 0
fun Byte.toBoolean(): Boolean = (this != 0.toByte())
