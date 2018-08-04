package com.opnay.todo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.opnay.todo.R
import com.opnay.todo.activity.MainActivity
import com.opnay.todo.view.ProgressText
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.android.synthetic.main.list_category.view.*

class CategoryFragment: BaseFragment() {

    private val parent: MainActivity by lazy { activity!! as MainActivity }

    // holder
    private var rootView: View? = null
    private val list: RecyclerView by lazy { rootView!!.categoryList }

    // Adapter
    private val adapter: TempAdapter by lazy { TempAdapter(parent) }
    private val viewManager: LinearLayoutManager by lazy { LinearLayoutManager(parent).apply {
        orientation = LinearLayoutManager.HORIZONTAL
    } }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_category, container, false)

        list.adapter = adapter
        list.layoutManager = viewManager

        return rootView
    }

    inner class TempAdapter(private val context: Context): RecyclerView.Adapter<TempAdapter.ViewHolder>() {

        private val layout = R.layout.list_category
        private val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
        val data: ArrayList<String> = arrayListOf("1", "2", "3", "Category", "Test", "Adapter")

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(layout, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = data[position]

            // Data Initial
            holder.apply {
                title.text = item
            }
        }

        override fun getItemCount(): Int = data.size

        inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
            val title: TextView = view.title
            val progress: ProgressText = view.complete
        }
    }
}