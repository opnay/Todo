package com.opnay.todo.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import at.markushi.ui.CircleButton
import com.opnay.todo.R
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
    private var catCur = -1
    private var catManage = false

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
                    updateList(true)
                R.id.menu_settings ->
                    Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_LONG).show()
                R.id.menu_info ->
                    Toast.makeText(this@MainActivity, "Info", Toast.LENGTH_LONG).show()
                else -> {
                    if (it.groupId == R.id.menu_category)
                        updateList(false, it.itemId)
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
        updateList(false, 0)
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
            if (catManage) {    // Add Category
                TodoPreference.catData.add(this)
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

    private fun updateCategory() {
        navigation.menu!!.run {
            // Cleanup Category on menu
            clear()
            navigation.inflateMenu(R.menu.navigation_menu)

            // Add Category on menu
            TodoPreference.catData.forEachIndexed { i, e ->
                add(R.id.menu_category, i, i, e).apply {
                    isChecked = (i == catCur)
                }
            }
            findItem(R.id.cat_manage).isChecked = catManage
        }
    }

    private fun updateList(manage: Boolean = catManage, state: Int = catCur) {
        if (!manage && state != catCur) {   // Show Category
            TodoPreference.catData[state].run {
                lstTodo.adapter =
                        if (state == 0) TodoAdapter(this@MainActivity, TodoPreference.prefData)
                        else TodoAdapter(
                                this@MainActivity,
                                ArrayList(TodoPreference.prefData.filter { it.category == this })
                        )
                supportActionBar!!.title = this
            }
        } else if (manage != catManage) {   // Show Category Manage
            lstTodo.adapter =
                    ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, TodoPreference.catData)
            lstTodo.setOnItemLongClickListener { _, _, i, _ ->
                AlertDialog.Builder(this@MainActivity, R.style.App_Dialog).run {
                    setTitle("Delete category?")
                    setMessage("All items which include this will delete with this.")
                    setPositiveButton("Delete") { _, _ ->
                        TodoPreference.catData.removeAt(i)
                        TodoPreference.savePref(this@MainActivity)
                        updateList()
                    }
                    setNegativeButton("Cancel") { _, _ -> }
                    create()
                    show()
                }
                true
            }
            supportActionBar!!.title = "Category Manage"
        }

        // Refresh ListView
        (lstTodo.adapter as BaseAdapter).notifyDataSetChanged()
        // When manage is true, catCur set as out of index
        catCur = if (manage) TodoPreference.catData.size else state
        catManage = manage

        // After this, update category
        updateCategory()
    }

}
