package com.opnay.todo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.opnay.todo.R
import com.opnay.todo.Util
import com.opnay.todo.activity.ItemActivity
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
        rootView = inflater.inflate(R.layout.list_category, container, false).apply {
            setOnClickListener {
                Util.startActivity(context!!, ItemActivity::class.java,
                        hashMapOf(ItemActivity.KEY_CATEGORY_INDEX to position))
            }
        }

        tvTitle.text = category

        return rootView
    }
}