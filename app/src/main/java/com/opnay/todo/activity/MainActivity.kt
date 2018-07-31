package com.opnay.todo.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.opnay.todo.R
import com.opnay.todo.fragment.ItemFragment
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // View Holder
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

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit()

        TodoPreference.loadPref(this)
    }

    override fun onStart() {
        super.onStart()
        // For Initial
        fragment.updateList(false, 0)
    }

    override fun onBackPressed() {
        if (!fragment.onBackPressed())
            super.onBackPressed()
    }
}
