package com.opnay.todo.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

val Context.db: ItemDatabaseHelper
    get() = ItemDatabaseHelper.getInstance(applicationContext)

class ItemDatabaseHelper(ctx: Context):
        ManagedSQLiteOpenHelper(ctx, "ItemDatabase", null, 1) {

    companion object {
        private var instance: ItemDatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): ItemDatabaseHelper =
                instance ?: ItemDatabaseHelper(ctx).also { instance = it }

        const val TABLE = "ITEM"
        const val ATTR_ID = "ID"
        const val ATTR_TITLE = "TITLE"
        const val ATTR_CATEGORY = "CATEGORY"
        const val ATTR_DESC = "DESC"
        const val ATTR_CHECK = "CHECK"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TABLE, true,
                ATTR_ID to INTEGER + PRIMARY_KEY + UNIQUE,
                ATTR_TITLE to TEXT,
                ATTR_CATEGORY to TEXT,
                ATTR_DESC to TEXT,
                ATTR_CHECK to INTEGER)
    }

    // Yet use
    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {}

}
