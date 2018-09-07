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
import com.opnay.todo.data.Category
import com.opnay.todo.view.ProgressText
import kotlinx.android.synthetic.main.list_category.view.*
import org.jetbrains.anko.startActivity

class CategoryItemFragment: Fragment() {

    // Holder
    private var rootView: View? = null
    private val tvTitle: TextView by lazy { rootView!!.title }
    private val prgComplete: ProgressText by lazy { rootView!!.complete }

    val category: Category by lazy { arguments!!.getParcelable(Util.KEY_CATEGORY) as Category }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.list_category, container, false).apply {
            setOnClickListener { _ ->
                context!!.startActivity<ItemActivity>(Util.KEY_CATEGORY to category)
            }
        }

        tvTitle.text = category.title

        val items = category.getItems(context!!)
        val per = Math.round(items.filter { it.check }.size.toFloat() / items.size * 100)
        prgComplete.progress = per

        return rootView
    }

    fun isChanged(): Boolean = (tvTitle.text != category.title)
}