package com.opnay.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_add_todo.*

class ModifyActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context, index: Int) {
            // index : -1 -> New Item
            Intent(context, ModifyActivity::class.java).run {
                putExtra("INDEX", index)
                context.startActivity(this)
            }
        }
    }

    private val dataIndex: Int by lazy { intent.getIntExtra("INDEX", -1) }
    private val data: TodoData by lazy { TodoPreference.prefData[dataIndex] }

    // View Holder
    private val tvTitle: TextInputEditText by lazy { edit_title.editText as TextInputEditText }
    private val tvDesc: TextInputEditText by lazy { edit_desc.editText as TextInputEditText }
    private val btnOK: Button by lazy { btn_ok }
    private val btnDel: Button by lazy { btn_del }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        setSupportActionBar(toolbar)

        btnOK.setOnClickListener {
            if (tvTitle.text.isEmpty()) {
                Snackbar.make(layout_todo, "제목을 다시 입력해주세요.", 1400)
                        .setAction("OK") { tvTitle.requestFocus() }
                        .show()
                return@setOnClickListener
            } else {
                saveData()
                finish()
            }
        }

        btnDel.apply {
            setOnClickListener {
                TodoPreference.prefData.removeAt(dataIndex)
                finish()
            }
        }

        // Load Data
        data.run {
            tvTitle.setText(title)
            tvDesc.setText(desc)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TodoPreference.savePref(this)
    }

    private fun saveData() {
        data.apply {
            title = tvTitle.text.toString()
            desc = tvDesc.text.toString()
        }
    }
}
