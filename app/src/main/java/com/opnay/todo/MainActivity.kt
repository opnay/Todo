package com.opnay.todo

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintLayout
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import at.markushi.ui.CircleButton
import com.opnay.todo.adapter.TodoAdapter
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ru.dimorinny.floatingtextbutton.FloatingTextButton

class MainActivity : AppCompatActivity() {
    private var addNew: Boolean = false
    private val adapter: TodoAdapter by lazy {
        TodoAdapter(this@MainActivity, TodoPreference.prefData)
    }
    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private val toggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close)
    }

    // View Holder
    private val drawer: DrawerLayout by lazy { main_root as DrawerLayout }
    private val lstTodo: ListView by lazy { main_list as ListView }
    private val fabAdd: FloatingTextButton by lazy { fab_btn_add as FloatingTextButton }
    private val layNew: ConstraintLayout by lazy { new_item as ConstraintLayout }
    private val btnNew: CircleButton by lazy { new_btn as CircleButton }
    private val etNew: EditText by lazy { new_title as EditText }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        fabAdd.setOnClickListener {
            showNewAdd(true)
            etNew.requestFocus()
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 1)
        }

        btnNew.isEnabled = false
        btnNew.setOnClickListener { addNewItem() }
        etNew.setOnKeyListener { _, code, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) and (code == KeyEvent.KEYCODE_ENTER))
                return@setOnKeyListener addNewItem()
            return@setOnKeyListener false
        }
        etNew.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnNew.isEnabled = etNew.text.isNotEmpty()
            }
        })

        TodoPreference.loadPref(this)
        lstTodo.adapter = adapter
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        TodoPreference.savePref(this)
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (addNew) {
            showNewAdd(false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    private fun showNewAdd(en: Boolean) {
        addNew = en
        if (en) {
            layNew.visibility = View.VISIBLE
            fabAdd.visibility = View.GONE
        } else {
            layNew.visibility = View.GONE
            fabAdd.visibility = View.VISIBLE
        }
    }

    private fun addNewItem(): Boolean {
        TodoData().apply {
            title = etNew.text.toString()
        }.run {
            TodoPreference.prefData.add(this)
            adapter.notifyDataSetChanged()
            TodoPreference.savePref(this@MainActivity)
        }
        lstTodo.smoothScrollByOffset(lstTodo.bottom)

        etNew.text.clear()
        showNewAdd(false)
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        return true
    }

}
