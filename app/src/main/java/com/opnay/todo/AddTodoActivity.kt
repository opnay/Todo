package com.opnay.todo

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
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

        btn_ok.setOnClickListener { _ ->
            if (edit_title.editText!!.text.isEmpty()) {
                Snackbar.make(layout_todo, "제목을 다시 입력해주세요.", 1400)
                        .setAction("OK", { _ ->
                            edit_title.requestFocus()
                        }).show()
                return@setOnClickListener
            } else {
                TodoPreference.prefData.add(TodoData(edit_title.editText!!.text.toString()))
                finish()
            }
        }
    }
}
