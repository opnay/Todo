package com.opnay.todo

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.opnay.todo.adapter.TodoAdapter
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val pref: SharedPreferences by lazy {
        getSharedPreferences("Todo", Activity.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_add.setOnClickListener { _ ->
            val i = Intent(this, AddTodoActivity::class.java)
            startActivity(i)
        }

        loadPref()
        main_list.adapter = TodoAdapter(this, TodoPreference.prefData)

    }

    override fun onResume() {
        super.onResume()
        (main_list.adapter as TodoAdapter).notifyDataSetChanged()
    }

    override fun onPause() {
        savePref()
        super.onPause()
    }

    private fun savePref() {
        pref.edit().run {
            putString("Data", Gson().toJson(TodoPreference.prefData))
            apply()
        }
    }

    private fun loadPref() {
        pref.getString("Data", "")?.run {
            val value: ArrayList<TodoData>? = Gson().fromJson(this, object : TypeToken<ArrayList<TodoData>>() {}.type)
            if (value != null) TodoPreference.prefData = value
        }
    }

}
