package com.opnay.todo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.opnay.todo.R
import com.opnay.todo.data.TodoData
import kotlinx.android.synthetic.main.list_todo.view.*

class TodoAdapter(val context: Context, val data: ArrayList<TodoData>)
    : BaseAdapter() {
    private val layout = R.layout.list_todo
    private lateinit var holder: ViewHolder
    private lateinit var view: View

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(layout, null)
            holder = ViewHolder(view)

            view.setOnClickListener({ v ->
                v.todo_check.toggle()
            })
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        holder.bindView(data[position])
        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return data.size
    }

    inner class ViewHolder(val view: View) {
        fun bindView(item: TodoData) {
            item.let {
                with(it) {
                    view.todo_text.text = title
                    view.todo_check.isChecked = finished
                }
            }
        }
    }

}
