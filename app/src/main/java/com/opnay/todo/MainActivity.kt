package com.opnay.todo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.opnay.todo.adapter.TodoAdapter
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var addNew: Boolean = false
    private val pref: SharedPreferences by lazy {
        getSharedPreferences("Todo", Activity.MODE_PRIVATE)
    }
    private val adapter: TodoAdapter by lazy {
        TodoAdapter(this@MainActivity, TodoPreference.prefData)
    }
    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_add.setOnClickListener {
            showNewAdd(true)
            new_title.requestFocus()
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 1)
        }

        new_btn.isEnabled = false
        new_btn.setOnClickListener {
            TodoData().apply {
                title = new_title.text.toString()
            }.run {
                TodoPreference.prefData.add(this)
                adapter.notifyDataSetChanged()
            }
            main_list.smoothScrollByOffset(main_list.bottom)

            new_title.text.clear()
            showNewAdd(false)
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
        new_title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                new_btn.isEnabled = new_title.text.isNotEmpty()
            }
        })

        loadPref()
        main_list.adapter = adapter
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if (newConfig!!.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
            showNewAdd(false)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        savePref()
        super.onPause()
    }

    override fun onBackPressed() {
        if (addNew) {
            showNewAdd(false)
        } else {
            super.onBackPressed()
        }
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

    private fun showNewAdd(en: Boolean) {
        addNew = en
        if (en) {
            new_item.visibility = View.VISIBLE
            main_add.visibility = View.GONE
        } else {
            new_item.visibility = View.GONE
            main_add.visibility = View.VISIBLE
        }
    }

}
