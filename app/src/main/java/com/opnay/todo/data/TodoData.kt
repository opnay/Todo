package com.opnay.todo.data

import android.os.Parcel
import android.os.Parcelable
import com.opnay.todo.toBoolean
import com.opnay.todo.toByte

class TodoData(var title: String = "") : Parcelable {
    var category: String = ""
    var desc: String = ""
    var check: Boolean = false

    fun toggle(t: Boolean = !check) {
        check = t
    }

    constructor(parcel: Parcel) : this(parcel.readString()) {
        category = parcel.readString()
        desc = parcel.readString()
        check = parcel.readByte().toBoolean()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(category)
        parcel.writeString(desc)
        parcel.writeByte(check.toByte())
    }

    override fun describeContents(): Int = 0
    companion object CREATOR : Parcelable.Creator<TodoData> {
        override fun createFromParcel(parcel: Parcel): TodoData = TodoData(parcel)
        override fun newArray(size: Int): Array<TodoData?> = arrayOfNulls(size)
    }
}
