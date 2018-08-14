package com.opnay.todo.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.opnay.todo.R
import com.opnay.todo.fragment.BaseFragment

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {

    val toolBar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    val actionBar: ActionBar by lazy { supportActionBar!! }
    var fragment: BaseFragment? = null

    // App Compat Toolbar back button
    fun ActionBar.enableBack(en: Boolean) {
        setDisplayHomeAsUpEnabled(en)
        setDisplayShowHomeEnabled(en)
        setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp)
    }

    override fun onSupportNavigateUp(): Boolean = true.also { onBackPressed() }

    // SupportFragmentManager
    // replace function simplify.
    fun FragmentManager.replace(fragment: BaseFragment) =
            this.beginTransaction().replace(R.id.fragmentFrame, fragment).commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)
        setSupportActionBar(toolBar)
    }

    override fun onStart() {
        super.onStart()
        // For Initial
        fragment?.updateData()
    }

    override fun onBackPressed() {
        if (fragment?.onBackPressed() == true)
            return

        super.onBackPressed()
    }
}