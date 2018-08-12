package com.opnay.todo.data

import android.os.Parcel
import android.os.Parcelable
import com.opnay.todo.toBoolean
import com.opnay.todo.toByte

data class TodoData(val id: Int,
                    var title: String = "",
                    var category: Int = 0,
                    var desc: String = "",
                    var check: Boolean = false) : Parcelable {

    fun toggle(t: Boolean = !check) {
        check = t
    }

    constructor(parcel: Parcel) :
            this(parcel.readInt(),
                    parcel.readString(),
                    parcel.readInt(),
                    parcel.readString(),
                    parcel.readByte().toBoolean())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeInt(category)
        parcel.writeString(desc)
        parcel.writeByte(check.toByte())
    }

    override fun describeContents(): Int = 0
    companion object CREATOR : Parcelable.Creator<TodoData> {
        override fun createFromParcel(parcel: Parcel): TodoData = TodoData(parcel)
        override fun newArray(size: Int): Array<TodoData?> = arrayOfNulls(size)
    }
}
