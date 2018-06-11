package com.opnay.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodoActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context, index: Int) {
            // index : -1 -> New Item
            Intent(context, AddTodoActivity::class.java).run {
                putExtra("INDEX", index)
                context.startActivity(this)
            }
        }
    }

    val dataIndex: Int by lazy {
        this@AddTodoActivity.intent.getIntExtra("INDEX", -1)
    }
    val data: TodoData by lazy {
        if (dataIndex >= 0)
            TodoPreference.prefData.get(dataIndex)
        else
            TodoData()
    }

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
                saveData()
                finish()
            }
        }

        // Load Data
        data.run {
            edit_title.editText!!.setText(title)
            edit_desc.editText!!.setText(desc)
        }
    }

    fun saveData() {
        data.apply {
            title = edit_title.editText!!.text.toString()
            desc = edit_desc.editText!!.text.toString()
        }. run {
            if (dataIndex < 0)
                TodoPreference.prefData.add(this)
        }
    }
}
