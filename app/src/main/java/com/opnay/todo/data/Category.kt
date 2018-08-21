package com.opnay.todo.data

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.opnay.todo.sqlite.ItemDatabaseHelper
import com.opnay.todo.sqlite.ItemDatabaseHelper.Companion.TABLE_CAT
import com.opnay.todo.sqlite.db

data class Category(val id: Int, var title: String): Parcelable {

    // Database
    fun update(ctx: Context) = ctx.db.update(TABLE_CAT, id, ItemDatabaseHelper.ATTR_TITLE to title)
    fun delete(ctx: Context): Int = ctx.db.delete(TABLE_CAT, id)
    fun getItems(ctx: Context): List<TodoData> = ctx.db.items.filter { it.category == id }


    // Parcelable
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString())
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int = 0
    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category = Category(parcel)
        override fun newArray(size: Int): Array<Category?> = arrayOfNulls(size)
    }
}