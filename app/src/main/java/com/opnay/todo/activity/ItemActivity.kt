package com.opnay.todo.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.opnay.todo.R
import com.opnay.todo.fragment.ItemFragment
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.activity_with_fragment.*

class ItemActivity: BaseActivity() {
    companion object {
        const val KEY_CATEGORY_INDEX = "CategoryIndex"
    }

    private val toolBar: Toolbar by lazy { toolbar }

    private val catPosition: Int by lazy { intent.getSerializableExtra(KEY_CATEGORY_INDEX) as Int }
    private val category: String
        get() = TodoPreference.catData[catPosition]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)
        setSupportActionBar(toolBar)

        actionBar.title = category

        fragment = ItemFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentFrame, fragment)
                .commit()
    }
}