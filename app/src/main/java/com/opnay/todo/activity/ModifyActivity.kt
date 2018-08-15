package com.opnay.todo.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.opnay.todo.Util
import com.opnay.todo.data.TodoData
import com.opnay.todo.fragment.ModifyFragment
import com.opnay.todo.sqlite.db
import com.opnay.todo.view.enableBack
import com.opnay.todo.view.setTitle

class ModifyActivity : BaseActivity() {
    private val data: TodoData by lazy { intent.getParcelableExtra(Util.KEY_ITEM) as TodoData }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.apply {
            // Enable back button
            enableBack(true, Color.WHITE)
            setTitle(this@ModifyActivity.db.category.find { it.id == data.category }!!)
        }

        toolBar.apply {
            setBackgroundColor(ContextCompat.getColor(this@ModifyActivity, android.R.color.transparent))
            setTitleTextColor(Color.WHITE)
        }

        supportFragmentManager.replace(ModifyFragment().also {
            it.arguments = Bundle().apply { putParcelable(Util.KEY_ITEM, data) }
        })
    }

    // Toolbar Back Button
    override fun onSupportNavigateUp(): Boolean = true.also { onBackPressed() }
}
