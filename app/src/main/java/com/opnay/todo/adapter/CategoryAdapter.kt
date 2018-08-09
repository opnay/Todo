package com.opnay.todo.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.opnay.todo.fragment.CategoryAddFragment
import com.opnay.todo.fragment.CategoryItemFragment
import com.opnay.todo.preference.TodoPreference

class CategoryAdapter(val context: Context, fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(pos: Int): Fragment {
        return if (pos >= TodoPreference.catData.size)
            CategoryAddFragment().apply { adapter = this@CategoryAdapter }
        else
            CategoryItemFragment().apply { position = pos }
    }
    override fun getCount(): Int = TodoPreference.catData.size + 1

}