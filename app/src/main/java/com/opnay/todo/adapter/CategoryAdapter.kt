package com.opnay.todo.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.opnay.todo.Util
import com.opnay.todo.data.Category
import com.opnay.todo.fragment.CategoryAddFragment
import com.opnay.todo.fragment.CategoryItemFragment
import com.opnay.todo.sqlite.db

class CategoryAdapter(val context: Context, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    val data: ArrayList<Category> by lazy { ArrayList<Category>(context.db.category) }

    override fun getItem(pos: Int): Fragment {
        return if (pos >= data.size)
            CategoryAddFragment().apply { adapter = this@CategoryAdapter }
        else
            CategoryItemFragment().apply {
                arguments = Bundle().also {
                    it.putParcelable(Util.KEY_CATEGORY, data[pos])
                }
            }
    }

    override fun getCount(): Int = data.size + 1

    override fun getItemPosition(item: Any): Int {
        if (item is CategoryItemFragment)
            if (!item.isChanged())
                return PagerAdapter.POSITION_UNCHANGED

        // Refresh
        return PagerAdapter.POSITION_NONE
    }

    private fun loadData() {
        // Load Category from db
        data.clear()
        data.addAll(context.db.category)
    }

    override fun notifyDataSetChanged() {
        loadData()
        super.notifyDataSetChanged()
    }
}