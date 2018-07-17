package com.opnay.todo

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import at.markushi.ui.CircleButton
import com.opnay.todo.adapter.TodoAdapter
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ru.dimorinny.floatingtextbutton.FloatingTextButton

class MainActivity : AppCompatActivity() {
    private var addNew: Boolean = false
    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private val toggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close)
    }

    // Category
    private var catNum = 0

    // ListView Category
    //  0   = Category Manage
    //  1   = All Category
    //  2 ~ = Other Category
    private var lstState = -1

    // View Holder
    private val drawer: DrawerLayout by lazy { main_root as DrawerLayout }
    private val navigation: NavigationView by lazy { main_nav as NavigationView }
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

        navigation.setNavigationItemSelectedListener {
            drawer.closeDrawer(navigation)

            when(it.itemId) {
                R.id.cat_manage ->
                    updateList(-1)
                R.id.menu_settings ->
                    Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_LONG).show()
                R.id.menu_info ->
                    Toast.makeText(this@MainActivity, "Info", Toast.LENGTH_LONG).show()
                else -> {
                    if (it.groupId == R.id.menu_category)
                        updateList(it.itemId)
                }

            }

            return@setNavigationItemSelectedListener true
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
            btnNew.isEnabled = etNew.text.isNotEmpty()
            return@setOnKeyListener false
        }

        TodoPreference.loadPref(this)

        // Initial
        updateList(0)

        // Add Category
        TodoPreference.catData.forEach { addCategory(it) }

        // Accent Category All
        navigation.menu.getItem(0).isChecked = true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onResume() {
        super.onResume()
        updateList()
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
        etNew.text.toString().run {
            if (lstState == -1) {    // Add Category
                TodoPreference.catData.add(this)
                addCategory(this)
            } else {
                TodoPreference.prefData.add(TodoData(title = this))
            }
        }

        updateList()
        lstTodo.smoothScrollByOffset(lstTodo.bottom)
        TodoPreference.savePref(this)

        etNew.text.clear()
        showNewAdd(false)
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        return true
    }

    private fun addCategory(title: String) {
        navigation.menu!!
                .add(R.id.menu_category, catNum, catNum, title)
        catNum += 1
    }

    private fun updateList(state: Int = lstState) {
        if (state != lstState) {
            when (state) {
                -1 -> {
                    lstTodo.adapter =
                            ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, TodoPreference.catData)
                    supportActionBar!!.title = "Category Manage"
                }
                else -> {
                    TodoPreference.catData[state].run {
                        lstTodo.adapter =
                                TodoAdapter(this@MainActivity, TodoPreference.prefData)
                        supportActionBar!!.title = this
                    }
                }
            }
        }

        (lstTodo.adapter as BaseAdapter).notifyDataSetChanged()
        lstState = state
    }

}
