package com.opnay.todo.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.opnay.todo.R
import com.opnay.todo.fragment.CategoryFragment
import kotlinx.android.synthetic.main.activity_with_fragment.*

class MainActivity : BaseActivity() {

    private val rootView: LinearLayout by lazy { rootLayout }
    private val toolBar: Toolbar by lazy { toolbar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)
        setSupportActionBar(toolBar)

        // Set Background
        rootView.setBackgroundResource(R.drawable.main_background)
        toolBar.apply {
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, android.R.color.transparent))
            setTitleTextColor(Color.WHITE)
        }

        fragment = CategoryFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentFrame, fragment)
                .commit()
    }

}
