package com.opnay.todo.activity

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.opnay.todo.R
import com.opnay.todo.fragment.ItemFragment
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val toggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close)
    }

    // Category
    private var catManage = false

    // View Holder
    private val drawer: DrawerLayout by lazy { main_root as DrawerLayout }
    private val navigation: NavigationView by lazy { main_nav as NavigationView }
    private val fragment: ItemFragment = ItemFragment()
    private val actionBar: ActionBar by lazy { supportActionBar!! }

    var title: String
        get() = actionBar.title as String
        set(value) {
            actionBar.title = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit()

        navigation.setNavigationItemSelectedListener {
            drawer.closeDrawer(navigation)

            when(it.itemId) {
                R.id.cat_manage ->
                    fragment.updateList(true)
                R.id.menu_settings ->
                    Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_LONG).show()
                R.id.menu_info ->
                    Toast.makeText(this@MainActivity, "Info", Toast.LENGTH_LONG).show()
                else -> {
                    if (it.groupId == R.id.menu_category)
                        fragment.updateList(false, it.itemId)
                }

            }

            return@setNavigationItemSelectedListener true
        }

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        TodoPreference.loadPref(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onStart() {
        super.onStart()
        // For Initial
        fragment.updateList(false, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (fragment.addNew) {
            fragment.showNewAdd(false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    fun updateCategory(curSel: Int) {
        navigation.menu!!.run {
            // Cleanup Category on menu
            clear()
            navigation.inflateMenu(R.menu.navigation_menu)

            // Add Category on menu
            TodoPreference.catData.forEachIndexed { i, e ->
                add(R.id.menu_category, i, i, e).apply {
                    isChecked = (i == curSel)
                }
            }
            findItem(R.id.cat_manage).isChecked = catManage
        }
    }
}
