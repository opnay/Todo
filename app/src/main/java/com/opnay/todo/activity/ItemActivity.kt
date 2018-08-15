package com.opnay.todo.activity

import android.os.Bundle
import com.opnay.todo.Util.Companion.KEY_CATEGORY
import com.opnay.todo.data.Category
import com.opnay.todo.fragment.ItemFragment
import com.opnay.todo.view.enableBack
import com.opnay.todo.view.setTitle

class ItemActivity: BaseActivity() {
    private val category: Category by lazy { intent.getParcelableExtra(KEY_CATEGORY) as Category }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar.apply {
            enableBack(true)
            setTitle(category)
        }

        supportFragmentManager.replace(ItemFragment().also{
            // BaseActivity fragment.
            fragment = it

            // Give category data
            it.arguments = Bundle().apply { putParcelable(KEY_CATEGORY, category) }
        })
    }
}