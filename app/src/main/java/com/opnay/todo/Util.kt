package com.opnay.todo

import android.content.Context
import android.content.Intent
import java.io.Serializable

class Util {
    companion object {
        fun startActivity(ctx: Context, cls: Class<*>) { startActivity(ctx, cls, null) }
        fun startActivity(context: Context, cls: Class<*>, extra: HashMap<String, Serializable>?) {
            Intent(context, cls).run {
                extra?.forEach { k, d -> this.putExtra(k, d) }
                context.startActivity(this)
            }
        }
    }

    class UtilData<T>(var data: T): Serializable
}
