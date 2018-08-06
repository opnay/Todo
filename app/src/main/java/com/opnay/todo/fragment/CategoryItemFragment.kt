package com.opnay.todo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.opnay.todo.R
import com.opnay.todo.preference.TodoPreference
import com.opnay.todo.view.ProgressText
import kotlinx.android.synthetic.main.list_category.view.*

class CategoryItemFragment: Fragment() {

    // Holder
    private var rootView: View? = null
    private val tvTitle: TextView by lazy { rootView!!.title }
    private val prgComplete: ProgressText by lazy { rootView!!.complete }

    var position: Int = 0
    private val category: String
        get() = TodoPreference.catData[position]

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.list_category, container, false)

        if (position >= 0) {
            TodoPreference.catData[position].run {
                tvTitle.text = this
            }
        }

        return rootView
    }
}