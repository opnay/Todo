package com.opnay.todo.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.opnay.todo.R
import com.opnay.todo.Util
import com.opnay.todo.activity.ModifyActivity
import com.opnay.todo.app.DetailDialog
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.list_todo.view.*

class TodoAdapter(private val context: Context, val data: ArrayList<TodoData>)
    : BaseAdapter() {
    private val layout = R.layout.list_todo
    private lateinit var holder: ViewHolder
    private lateinit var view: View
    private val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        view = convertView ?: inflater.inflate(layout, null)
        holder = (view.tag as ViewHolder?) ?: ViewHolder(view).also { view.tag = it }

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

                // Save Checkbox
                TodoPreference.savePref(context)
            }
        }
        return view
    }

    override fun getItem(position: Int): Any = data[position]
    override fun getItemId(position: Int): Long = 0
    override fun getCount(): Int = data.size

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
