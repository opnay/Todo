package com.opnay.todo.preference

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.opnay.todo.data.TodoData

class TodoPreference {
    companion object {
        var prefData: ArrayList<TodoData> = ArrayList()
        var catData: ArrayList<String> = arrayListOf("All")
        val gson: Gson = Gson()

        fun savePref(context: Context) {
            context.getSharedPreferences("Todo", Activity.MODE_PRIVATE)
                    .edit().run {
                        putString("Data", gson.toJson(TodoPreference.prefData))
                        putString("Category", gson.toJson(TodoPreference.catData))
                        apply()
                    }
        }
        fun loadPref(context: Context) {
            context.getSharedPreferences("Todo", Activity.MODE_PRIVATE).run {
                getString("Data", "").run {
                    gson.fromJson<ArrayList<TodoData>>(this, object: TypeToken<ArrayList<TodoData>>() {}.type)
                            ?.run { prefData = this }
                }
                getString("Category", "").run {
                    gson.fromJson<ArrayList<String>>(this, object: TypeToken<ArrayList<String>>() {}.type)
                            ?.run { catData = this }
                }
            }
        }

    }
}
