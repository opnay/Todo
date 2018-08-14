package com.opnay.todo.fragment

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opnay.todo.R
import com.opnay.todo.activity.MainActivity
import com.opnay.todo.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.view.*

class CategoryFragment: BaseFragment() {

    private val parent: MainActivity by lazy { activity!! as MainActivity }

    // holder
    private var rootView: View? = null
    private val pagerCategory: ViewPager by lazy { rootView!!.categoryPager }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_category, container, false)

        pagerCategory.offscreenPageLimit = 5
        pagerCategory.adapter = CategoryAdapter(parent, fragmentManager!!)

        return rootView
    }

    override fun updateData() {
        pagerCategory.adapter!!.notifyDataSetChanged()
    }
}