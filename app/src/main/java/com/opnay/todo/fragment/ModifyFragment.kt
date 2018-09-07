package com.opnay.todo.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.opnay.todo.R
import com.opnay.todo.Util
import com.opnay.todo.activity.BaseActivity
import com.opnay.todo.data.TodoData
import kotlinx.android.synthetic.main.fragment_modify.view.*

class ModifyFragment: BaseFragment() {
    private val parent: BaseActivity by lazy { activity!! as BaseActivity }

    // Data
    private val data: TodoData by lazy { arguments!!.getParcelable<TodoData>(Util.KEY_ITEM) }

    // View Holder
    private var rootView: View? = null
    private val tvTitle: EditText by lazy { rootView!!.edit_title }
    private val tvDesc: EditText by lazy { rootView!!.edit_desc }
    private val btnOK: Button by lazy { rootView!!.btn_ok }
    private val btnDel: Button by lazy { rootView!!.btn_del }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_modify, container, false)

        btnOK.setOnClickListener { _ ->
            if (tvTitle.text.isEmpty()) {
                Snackbar.make(rootView!!, "제목을 다시 입력해주세요.", Snackbar.LENGTH_LONG)
                        .setAction("OK") { tvTitle.requestFocus() }
                        .show()
                return@setOnClickListener
            } else {
                // Save to db
                data.apply {
                    title = tvTitle.text.toString()
                    desc = tvDesc.text.toString()
                }.update(parent)

                parent.finish()
            }
        }

        btnDel.setOnClickListener { _ ->
            data.delete(parent)
            parent.finish()
        }

        // Load Data
        data.run {
            tvTitle.setText(title)
            tvDesc.setText(desc)
        }
        return rootView
    }
}