package com.opnay.todo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import com.opnay.todo.AddTodoActivity
import com.opnay.todo.R
import com.opnay.todo.data.TodoData
import kotlinx.android.synthetic.main.list_todo.view.*

class TodoAdapter(private val context: Context, val data: ArrayList<TodoData>)
    : BaseAdapter() {
    private val layout = R.layout.list_todo
    private lateinit var holder: ViewHolder
    private lateinit var view: View

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(layout, null)
            holder = ViewHolder(view)

            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        holder.bindView(data[position])

        view.setOnClickListener {
            AddTodoActivity.startActivity(context, position)
        }
        view.todo_check.setOnClickListener {
            data[position].toggle((it as CheckBox).isChecked)
        }
        return view
    }

    override fun getItem(position: Int): Any { return data[position] }
    override fun getItemId(position: Int): Long { return 0 }
    override fun getCount(): Int { return data.size }

    inner class ViewHolder(val view: View) {
        fun bindView(item: TodoData) {
            item.run {
                view.todo_text.text = title
                view.todo_check.isChecked = check
            }
        }
    }

}
