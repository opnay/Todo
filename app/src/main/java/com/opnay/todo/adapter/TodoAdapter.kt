package com.opnay.todo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opnay.todo.R
import com.opnay.todo.Util
import com.opnay.todo.activity.ModifyActivity
import com.opnay.todo.app.DetailDialog
import com.opnay.todo.data.TodoData
import kotlinx.android.synthetic.main.list_todo.view.*
import org.jetbrains.anko.startActivity

class TodoAdapter(private val context: Context, val data: ArrayList<TodoData>)
    : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private val layout = R.layout.list_todo
    private val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }

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
            chk.setOnCheckedChangeListener { _, ch ->
                // View Update
                isChecked = ch

                // Data Update
                item.apply {
                    check = ch
                }.update(context)
            }

            view.apply {
                setOnClickListener { _ -> DetailDialog(context, item).show() }
                setOnLongClickListener { _ ->
                    context.startActivity<ModifyActivity>(Util.KEY_ITEM to item)
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
