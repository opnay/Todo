package com.opnay.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.opnay.todo.adapter.TodoAdapter
import com.opnay.todo.data.TodoData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var data: ArrayList<TodoData>
    private val adapter: TodoAdapter by lazy {
        TodoAdapter(this, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = ArrayList()
        data.add(TodoData("Test1"))
        data.add(TodoData("Test2"))
        data.add(TodoData("Test3"))
        data.add(TodoData("Test4"))
        data.add(TodoData("Test5"))

        main_list.adapter = adapter

    }
}
