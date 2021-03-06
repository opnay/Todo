package com.opnay.todo.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.opnay.todo.data.Category
import com.opnay.todo.data.TodoData
import com.opnay.todo.toBoolean
import com.opnay.todo.toInt
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
                            (columns.getValue(ATTR_CATEGORY) as Long).toInt(),
                            (columns.getValue(ATTR_DESC) as String),
                            (columns.getValue(ATTR_COMPLETE) as Long).toBoolean())
        }

        val CategoryParser: MapRowParser<Category> = object: MapRowParser<Category> {
            override fun parseRow(columns: Map<String, Any?>): Category =
                    Category((columns.getValue(ATTR_ID) as Long).toInt(),
                            columns.getValue(ATTR_TITLE) as String)
        }
    }

    val category: List<Category>
        get() = select(TABLE_CAT, CategoryParser)
    val items: List<TodoData>
        get() = select(TABLE_ITEM, TodoParser)

    fun insertCategory(title: String) {
        use {
            insert(ItemDatabaseHelper.TABLE_CAT,
                    ItemDatabaseHelper.ATTR_TITLE to title)
        }
    }

    fun insertItem(title: String, category: Int) {
        use {
            insert(TABLE_ITEM,
                    ATTR_TITLE to title,
                    ATTR_CATEGORY to category,
                    ATTR_DESC to "",
                    ATTR_COMPLETE to false.toInt())
        }
    }

    fun <T: Any>select(table: String, parser: MapRowParser<T>) =
            use { select(table).parseList(parser) }
    fun update(table: String, id: Int, vararg values: Pair<String, Any?>) =
            use { update(table, *values).whereArgs("$ATTR_ID = {i}", "i" to id).exec() }
    fun delete(table: String, id: Int): Int =
            use { delete(table, "$ATTR_ID = {itemID}", "itemID" to id) }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TABLE_ITEM, true,
                ATTR_ID to INTEGER + PRIMARY_KEY + UNIQUE,
                ATTR_TITLE to TEXT,
                ATTR_CATEGORY to INTEGER,
                ATTR_DESC to TEXT,
                ATTR_COMPLETE to INTEGER)

        db.createTable(TABLE_CAT, true,
                ATTR_ID to INTEGER + PRIMARY_KEY + UNIQUE,
                ATTR_TITLE to TEXT)
    }

    // Yet use
    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {}
}
