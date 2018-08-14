package com.opnay.todo.data

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.opnay.todo.sqlite.db

data class Category(val id: Int, var title: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString())

    fun update(ctx: Context): Category {
        ctx.db.updateCategory(id, title)
        return this
    }

    fun delete(ctx: Context): Int = ctx.db.deleteCategory(id)

    fun getItems(ctx: Context): List<TodoData> = ctx.db.items.filter { it.category == id }

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