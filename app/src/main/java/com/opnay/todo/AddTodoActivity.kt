package com.opnay.todo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.android.synthetic.main.content_add_todo.*

class AddTodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { _ ->
            TodoPreference.prefData.add(TodoData(edit_title_layout.editText!!.text.toString()))
            finish()
        }
        add_todo_coordinator.setOnClickListener { _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window.currentFocus.windowToken, 0)
            window.currentFocus.clearFocus()
        }
    }
}
