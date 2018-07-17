package com.opnay.todo.data

class TodoData(var title: String = "") {
    var category: String = ""
    var desc: String = ""
    var check: Boolean = false

    fun toggle(t: Boolean = !check) {
        check = t
    }
}
