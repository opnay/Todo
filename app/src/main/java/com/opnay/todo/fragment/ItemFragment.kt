package com.opnay.todo.fragment

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import at.markushi.ui.CircleButton
import com.opnay.todo.R
import com.opnay.todo.Util.Companion.KEY_CATEGORY
import com.opnay.todo.activity.BaseActivity
import com.opnay.todo.adapter.TodoAdapter
import com.opnay.todo.data.Category
import com.opnay.todo.data.TodoData
import com.opnay.todo.sqlite.db
import kotlinx.android.synthetic.main.fragment_item_list.view.*
import ru.dimorinny.floatingtextbutton.FloatingTextButton

class ItemFragment: BaseFragment() {

    private val parent: BaseActivity by lazy { activity!! as BaseActivity }
    private val imm: InputMethodManager by lazy {
        parent.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private val category: Category by lazy { arguments!!.getParcelable(KEY_CATEGORY) as Category }
    private val items: ArrayList<TodoData> by lazy {
        ArrayList(parent.db.items.filter { it.category == category.id })
    }

    // Adapter
    private val allItemsAdapter: TodoAdapter by lazy { TodoAdapter(parent, items) }
    private val viewManager: LinearLayoutManager by lazy { LinearLayoutManager(parent) }

    // Holder
    private var rootView: View? = null
    private val lstTodo: RecyclerView by lazy { rootView!!.main_list as RecyclerView }
    private val fabAdd: FloatingTextButton by lazy { rootView!!.fab_btn_add as FloatingTextButton }
    private val layNew: ConstraintLayout by lazy { rootView!!.new_item as ConstraintLayout }
    private val btnNew: CircleButton by lazy { rootView!!.new_btn as CircleButton }
    private val etNew: EditText by lazy { rootView!!.new_title as EditText }

    /*
     * Item Fragment Mode
     *
     * @DEFAULT Show only list
     * @ADD     Show input with list
     * @MULTI   Selectable List (Will)
     */
    enum class Mode {
        DEFAULT,
        ADD,
        MULTI   // Will be add
    }

    private var mode: Mode = Mode.DEFAULT
        set(value) {
            // Check already
            if (field == value)
                return

            // Change mode
            when (value) {
                Mode.DEFAULT -> {
                    layNew.visibility = View.GONE
                    fabAdd.visibility = View.VISIBLE
                }
                Mode.ADD -> {
                    layNew.visibility = View.VISIBLE
                    fabAdd.visibility = View.GONE
                }
                Mode.MULTI -> {} // unused
            }

            // Replace value
            field = value
        }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_item_list, container, false)

        fabAdd.setOnClickListener {
            mode = Mode.ADD
            etNew.requestFocus()
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 1)
        }

        btnNew.isEnabled = false
        btnNew.setOnClickListener { addNewItem() }
        etNew.setOnKeyListener { _, code, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) and (code == KeyEvent.KEYCODE_ENTER))
                return@setOnKeyListener addNewItem()
            btnNew.isEnabled = etNew.text.isNotEmpty()
            return@setOnKeyListener false
        }

        lstTodo.adapter = allItemsAdapter
        lstTodo.layoutManager = viewManager

        return rootView
    }

    override fun onBackPressed(): Boolean {
        if (mode != Mode.DEFAULT) {
            mode = Mode.DEFAULT
            return true
        }

        return false
    }

    fun addNewItem(): Boolean {
        // Insert New Item
        parent.db.insertItem(etNew.text.toString(), category.id)

        // Notify data was changed
        updateData()
        lstTodo.smoothScrollToPosition(lstTodo.bottom)

        // Change Mode
        mode = Mode.DEFAULT
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

        // Clear Text Input
        etNew.text.clear()

        return true
    }

    override fun updateData() {
        items.clear()
        items.addAll(parent.db.items.filter { it.category == category.id })
        allItemsAdapter.notifyDataSetChanged()
    }
}