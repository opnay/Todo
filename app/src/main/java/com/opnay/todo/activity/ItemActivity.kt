package com.opnay.todo.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import com.opnay.todo.R
import com.opnay.todo.fragment.ItemFragment
import kotlinx.android.synthetic.main.activity_with_fragment.*

class ItemActivity: BaseActivity() {

    private val actionBar: ActionBar by lazy { supportActionBar!! }
    private val toolBar: Toolbar by lazy { toolbar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)
        setSupportActionBar(toolBar)

        actionBar.title = "CATEGORY_TEST"

        fragment = ItemFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentFrame, fragment)
                .commit()
    }
}