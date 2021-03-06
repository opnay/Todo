package com.opnay.todo.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import com.opnay.todo.R
import com.opnay.todo.fragment.CategoryFragment
import com.opnay.todo.getColorFromCompat
import kotlinx.android.synthetic.main.activity_with_fragment.*

class MainActivity : BaseActivity() {

    private val rootView: LinearLayout by lazy { rootLayout }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set Background
        rootView.setBackgroundResource(R.drawable.main_background)
        toolBar.apply {
            setBackgroundColor(this@MainActivity.getColorFromCompat(android.R.color.transparent))
            setTitleTextColor(Color.WHITE)
        }

        supportFragmentManager.replace(CategoryFragment().also { fragment = it })
    }

}
