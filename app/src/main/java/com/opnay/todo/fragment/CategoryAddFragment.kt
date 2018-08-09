package com.opnay.todo.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.opnay.todo.R
import com.opnay.todo.adapter.CategoryAdapter
import com.opnay.todo.preference.TodoPreference

class CategoryAddFragment: Fragment() {

    private var rootView: View? = null
    var adapter: CategoryAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.list_category_add, container, false).apply {
            setOnClickListener {
                AlertDialog.Builder(context).also {
                    val edit: EditText = EditText(context)
                    it.setView(edit)
                    it.setTitle("Title of Category")
                    it.setPositiveButton("OK") { _, _ ->
                        TodoPreference.catData.add(edit.text.toString())
                        adapter!!.notifyDataSetChanged()
                    }
                    it.setNegativeButton("Cancel", null)
                }.show()
            }
        }

        return rootView
    }
}