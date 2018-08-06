package com.opnay.todo.activity

import android.annotation.SuppressLint
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.opnay.todo.fragment.BaseFragment

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {

    val actionBar: ActionBar by lazy { supportActionBar!! }
    var fragment: BaseFragment? = null

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