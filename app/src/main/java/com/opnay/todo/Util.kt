package com.opnay.todo

import android.content.Context
import android.content.Intent
import android.os.Parcel
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
