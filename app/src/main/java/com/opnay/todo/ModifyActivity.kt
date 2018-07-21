package com.opnay.todo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_modify.*

class ModifyActivity : AppCompatActivity() {
    private val dataIndex: Int by lazy {
        (intent.getSerializableExtra("INDEX") as Util.UtilData<*>).data as Int
    }
    private val data: TodoData by lazy { TodoPreference.prefData[dataIndex] }

    private val spinAdapter by lazy {
        ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
                .apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    addAll(TodoPreference.catData)
                }
    }

    // View Holder
    private val spinCategory: Spinner by lazy { spin_category as Spinner }
    private val tvTitle: TextInputEditText by lazy { edit_title.editText as TextInputEditText }
    private val tvDesc: TextInputEditText by lazy { edit_desc.editText as TextInputEditText }
    private val btnOK: Button by lazy { btn_ok }
    private val btnDel: Button by lazy { btn_del }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

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
                TodoPreference.savePref(this@ModifyActivity)
                finish()
            }
        }

        spinCategory.adapter = spinAdapter

        // Load Data
        data.run {
            tvTitle.setText(title)
            tvDesc.setText(desc)
            spinAdapter.getPosition(category).let {
                if (it > 0)
                    spinCategory.setSelection(it)
                else
                    spinCategory.setSelection(0)
            }
        }
    }

    private fun saveData() {
        data.apply {
            title = tvTitle.text.toString()
            desc = tvDesc.text.toString()
            category = spinCategory.selectedItem.toString()
        }
        TodoPreference.savePref(this)
    }
}
