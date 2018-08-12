package com.opnay.todo.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.opnay.todo.data.TodoData
import com.opnay.todo.toBoolean
import org.jetbrains.anko.db.*

val Context.db: ItemDatabaseHelper
    get() = ItemDatabaseHelper.getInstance(applicationContext)

class ItemDatabaseHelper(ctx: Context):
        ManagedSQLiteOpenHelper(ctx, "ItemDB", null, 1) {

    companion object {
        private var instance: ItemDatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): ItemDatabaseHelper =
                instance ?: ItemDatabaseHelper(ctx).also { instance = it }

        const val TABLE_CAT = "CATEGORY"
        const val TABLE_ITEM = "ITEM"

        const val ATTR_ID = "ID"
        const val ATTR_TITLE = "TITLE"
        const val ATTR_CATEGORY = "CATEGORY"
        const val ATTR_DESC = "DESC"
        const val ATTR_COMPLETE = "COMPLETE"

        val TodoParser: MapRowParser<TodoData> = object: MapRowParser<TodoData> {
            override fun parseRow(columns: Map<String, Any?>): TodoData =
                    TodoData((columns.getValue(ATTR_ID) as Long).toInt(),
                            columns.getValue(ATTR_TITLE) as String,
                            columns.getValue(ATTR_CATEGORY) as String,
                            columns.getValue(ATTR_DESC) as String,
                            (columns.getValue(ATTR_COMPLETE) as Long).toBoolean())
        }
    }

    val category: List<String>
        get() = use {
            select(TABLE_CAT, ATTR_TITLE)
                    .parseList(StringParser)
        }

    fun insertCategory(title: String) {
        use {
            insert(ItemDatabaseHelper.TABLE_CAT,
                    ItemDatabaseHelper.ATTR_TITLE to title)
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TABLE_ITEM, true,
                ATTR_ID to INTEGER + PRIMARY_KEY + UNIQUE,
                ATTR_TITLE to TEXT,
                ATTR_CATEGORY to TEXT,
                ATTR_DESC to TEXT,
                ATTR_COMPLETE to INTEGER)

        db.createTable(TABLE_CAT, true,
                ATTR_ID to INTEGER + PRIMARY_KEY + UNIQUE,
                ATTR_TITLE to TEXT)
    }

    // Yet use
    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {}
}
