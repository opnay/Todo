package com.opnay.todo.data

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.opnay.todo.sqlite.ItemDatabaseHelper.Companion.TABLE_ITEM
import com.opnay.todo.sqlite.db
import com.opnay.todo.toBoolean
import com.opnay.todo.toByte
import com.opnay.todo.toInt

data class TodoData(val id: Int, var title: String = "", var category: Int = 0,
                    var desc: String = "", var check: Boolean = false) : Parcelable {

    // Database
    fun update(ctx: Context): TodoData = this.apply { ctx.db.updateItem(id, title, desc, check.toInt()) }
    fun delete(ctx: Context): Int = ctx.db.delete(TABLE_ITEM, id)


    // Parcelable
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readInt(),
            parcel.readString(), parcel.readByte().toBoolean())

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
