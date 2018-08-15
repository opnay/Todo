package com.opnay.todo.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import com.opnay.todo.R
import com.opnay.todo.Util
import com.opnay.todo.data.TodoData
import com.opnay.todo.sqlite.db
import com.opnay.todo.view.enableBack
import com.opnay.todo.view.setTitle
import kotlinx.android.synthetic.main.activity_modify.*

class ModifyActivity : AppCompatActivity() {
    private val data: TodoData by lazy { intent.getParcelableExtra(Util.KEY_ITEM) as TodoData }

    // View Holder
    private val toolBar: Toolbar by lazy { toolbar }
    private val tvTitle: EditText by lazy { edit_title }
    private val tvDesc: TextInputEditText by lazy { edit_desc.editText as TextInputEditText }
    private val btnOK: Button by lazy { btn_ok }
    private val btnDel: Button by lazy { btn_del }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        setSupportActionBar(toolBar)

        supportActionBar!!.apply {
            // Enable back button
            enableBack(true)
            setTitle(this@ModifyActivity.db.category.find { it.id == data.category }!!)
        }

        toolBar.apply {
            setBackgroundColor(ContextCompat.getColor(this@ModifyActivity, android.R.color.transparent))
            setTitleTextColor(Color.WHITE)
        }

        btnOK.setOnClickListener {
            if (tvTitle.text.isEmpty()) {
                Snackbar.make(layout_todo, "제목을 다시 입력해주세요.", Snackbar.LENGTH_LONG)
                        .setAction("OK") { tvTitle.requestFocus() }
                        .show()
                return@setOnClickListener
            } else {
                // Save to db
                data.apply {
                    title = tvTitle.text.toString()
                    desc = tvDesc.text.toString()
                }.update(this)

                finish()
            }
        }

        btnDel.setOnClickListener {
            db.deleteItem(data.id)
            finish()
        }

        // Load Data
        data.run {
            tvTitle.setText(title)
            tvDesc.setText(desc)
        }
    }

    // Toolbar Back Button
    override fun onSupportNavigateUp(): Boolean = true.also { onBackPressed() }
}
