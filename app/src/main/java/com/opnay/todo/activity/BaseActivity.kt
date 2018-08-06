package com.opnay.todo.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import com.opnay.todo.fragment.BaseFragment

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {
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