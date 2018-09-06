package com.opnay.todo.app

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.opnay.todo.R
import com.opnay.todo.data.TodoData
import kotlinx.android.synthetic.main.dialog_detail.*
import ru.dimorinny.floatingtextbutton.FloatingTextButton

class DetailDialog(context: Context, val data: TodoData): Dialog(context, R.style.App_Dialog) {

    // View Holder
    private val layoutDesc: LinearLayout by lazy { layout_desc }

    private val tvTitle: TextView by lazy { title }
    private val tvDesc: TextView by lazy { desc }
    private val btnOK: FloatingTextButton by lazy { dialog_ok as FloatingTextButton }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window!!.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(R.layout.dialog_detail)

        // Data binding
        data.run {
            tvTitle.text = this.title

            // FIXME: We have not date, It will be added.
            // Temporary use category
//            tvDate.text = if (this.category.isEmpty()) "All" else this.category

            // Description Check
            if (this.desc.isNotEmpty())
                // FIXME: Last line is not shown. Add endline for temporary fix it
                tvDesc.text = (this.desc + "\n")
            else
                layoutDesc.visibility = View.GONE
        }

        // Done Button
        btnOK.setOnClickListener { this.dismiss() }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // FIXME: Floating Text Button's radius is not work on onCreate function
        // if move this at onCreate function, Floating Text Button height is 0.
        (btnOK.rootView.findViewById(ru.dimorinny.floatingtextbutton.R.id.layout_button_container) as CardView).apply {
            Log.d("DetailDialog", "${this.height.toFloat()} / ${this.height} / ${this.radius} / ${this.measuredHeight}")
            addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                Log.d("DetailDialog", "${this.height.toFloat()} / ${this.height} / ${this.radius} / ${this.measuredHeight}")
                radius = (this.height / 2).toFloat()
            }
            radius = (this.height / 2).toFloat()
        }
    }

    override fun onBackPressed() = dismiss()
}