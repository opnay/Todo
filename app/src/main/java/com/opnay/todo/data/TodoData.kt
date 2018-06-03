package com.opnay.todo.data

class TodoData(var title: String, var finished: Boolean = false) {
    fun toggle() {
        finished = !finished
    }
}
