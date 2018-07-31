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

        holder.bindView(position)

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

    inner class ViewHolder(private val view: View) {
        private val tv = view.todo_text!!
        private val chk = view.todo_check!!

        fun bindView(position: Int) {
            val item = data[position]

            // TextView
            tv.text = item.title

            // Checkbox
            chk.apply {
                isChecked = item.check
                toggleStrike(tv, item.check)
                setOnClickListener {
                    item.toggle(chk.isChecked)
                    toggleStrike(tv, chk.isChecked)

                    // Save Checkbox
                    TodoPreference.savePref(context)
                }
            }

            // List item listener
            view.apply {
                setOnClickListener { DetailDialog(context, item).show() }
                setOnLongClickListener {
                    Util.startActivity(context, ModifyActivity::class.java,
                            hashMapOf("INDEX" to Util.UtilData(position)))
                    true
                }
            }
        }
    }

}
