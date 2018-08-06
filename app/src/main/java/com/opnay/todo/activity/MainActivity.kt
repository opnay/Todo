package com.opnay.todo.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import com.opnay.todo.R
import com.opnay.todo.fragment.CategoryFragment
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_with_fragment.*

class MainActivity : BaseActivity() {

    private val actionBar: ActionBar by lazy { supportActionBar!! }

    var title: String
        get() = actionBar.title as String
        set(value) {
            actionBar.title = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)
        setSupportActionBar(toolbar)

        fragment = CategoryFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentFrame, fragment)
                .commit()

        TodoPreference.loadPref(this)
    }

}
