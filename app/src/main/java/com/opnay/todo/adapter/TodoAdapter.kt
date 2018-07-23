package com.opnay.todo.adapter

import android.content.Context
import android.graphics.Paint
import android.view.*
import android.widget.BaseAdapter
import android.widget.TextView
import com.opnay.todo.ModifyActivity
import com.opnay.todo.R
import com.opnay.todo.Util
import com.opnay.todo.app.DetailDialog
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

        view.apply {
            setOnClickListener { DetailDialog(context, data[position]).show() }
            setOnLongClickListener {
                Util.startActivity(context, ModifyActivity::class.java,
                        hashMapOf("INDEX" to Util.UtilData(position)))
                true
            }
        }
        holder.chk.setOnClickListener {
            with((it.parent as View).tag as ViewHolder) {
                data[position].toggle(chk.isChecked)
                toggleStrike(tv, chk.isChecked)
            }
        }
        return view
    }

    override fun getItem(position: Int): Any { return data[position] }
    override fun getItemId(position: Int): Long { return 0 }
    override fun getCount(): Int { return data.size }

    private fun toggleStrike(tv: TextView, en: Boolean = false) {
        tv.apply {
            paintFlags =
                    if(en) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    inner class ViewHolder(val view: View) {
        val tv = view.todo_text!!
        val chk = view.todo_check!!
        fun bindView(item: TodoData) {
            item.run {
                tv.text = title
                chk.isChecked = check
                toggleStrike(tv, check)
            }
        }
    }

}
