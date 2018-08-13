package com.opnay.todo.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.opnay.todo.R
import com.opnay.todo.Util.Companion.KEY_CATEGORY
import com.opnay.todo.data.Category
import com.opnay.todo.fragment.ItemFragment
import kotlinx.android.synthetic.main.activity_with_fragment.*

class ItemActivity: BaseActivity() {

    private val toolBar: Toolbar by lazy { toolbar }

    private val category: Category by lazy { intent.getParcelableExtra(KEY_CATEGORY) as Category }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)
        setSupportActionBar(toolBar)

        actionBar.title = category.title

        fragment = ItemFragment().apply {
            arguments = Bundle().also {
                it.putParcelable(KEY_CATEGORY, category)
            }
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentFrame, fragment)
                .commit()
    }
}