package com.opnay.todo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.opnay.todo.R
import com.opnay.todo.app.DetailDialog
import com.opnay.todo.data.TodoData
import com.opnay.todo.sqlite.db
import kotlinx.android.synthetic.main.list_todo.view.*

class TodoAdapter(private val context: Context, val category: String, val categoryIdx: Int)
    : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private val layout = R.layout.list_todo
    private val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    private val data: ArrayList<TodoData> by lazy {
        ArrayList(context.db.items.filter { it.category == categoryIdx })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        // Data Initial
        holder.apply {
            tv.text = item.title
            isChecked = item.check

            // Checkbox
            chk.setOnClickListener {
                item.apply {
                    check != (it as CheckBox).isChecked
                }.update(context)
            }

            view.apply {
                setOnClickListener { DetailDialog(context, item).show() }
                setOnLongClickListener {
//                    Util.startActivity(context, ModifyActivity::class.java,
//                            hashMapOf("INDEX" to position))
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        // View
        val tv = view.todo_text!!
        val chk = view.todo_check!!

        /*
         * @true    Checked / TextDisable
         * @false   Unchecked
         */
        var isChecked: Boolean = true
            set(value) {
                field = value
                chk.isChecked = value
                tv.isEnabled = !value   // Toggle TextColor
            }
    }

}
