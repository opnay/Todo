package com.opnay.todo

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.opnay.todo.adapter.TodoAdapter
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private var addNew: Boolean = false
    private val adapter: TodoAdapter by lazy {
        TodoAdapter(this@MainActivity, TodoPreference.prefData)
    }
    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

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

        TodoPreference.loadPref(this)
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
        TodoPreference.savePref(this)
        super.onPause()
    }

    override fun onBackPressed() {
        if (addNew) {
            showNewAdd(false)
        } else {
            super.onBackPressed()
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
