package com.opnay.todo.fragment

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import at.markushi.ui.CircleButton
import com.opnay.todo.R
import com.opnay.todo.activity.MainActivity
import com.opnay.todo.adapter.TodoAdapter
import com.opnay.todo.data.TodoData
import com.opnay.todo.preference.TodoPreference
import kotlinx.android.synthetic.main.fragment_item_list.view.*
import ru.dimorinny.floatingtextbutton.FloatingTextButton

class ItemFragment: BaseFragment() {

    // Category
    private var catCur: Int = -1
    var catManage: Boolean = false

    private val parent: MainActivity by lazy { activity!! as MainActivity }
    private val imm: InputMethodManager by lazy {
        parent.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    // Adapter
    private val categoryAdapter: ArrayAdapter<String> by lazy {
            ArrayAdapter(parent, android.R.layout.simple_list_item_1, TodoPreference.catData)
    }
    private val allItemsAdapter: TodoAdapter
            by lazy { TodoAdapter(parent, TodoPreference.prefData) }

    // Listener
    private val categoryLongClickListener by lazy {
        AdapterView.OnItemLongClickListener { _, _, i, _ ->
            AlertDialog.Builder(parent, R.style.App_Dialog).apply {
                setTitle("Delete category?")
                setMessage("All items which include this will delete with this.")
                setPositiveButton("Delete") { _, _ ->
                    TodoPreference.catData.removeAt(i)
                    TodoPreference.savePref(parent)
                    updateList()
                }
                setNegativeButton("Cancel") { _, _ -> }
                create()
            }.show()
            return@OnItemLongClickListener true
        }
    }

    // Holder
    private var rootView: View? = null
    private val lstTodo: ListView by lazy { rootView!!.main_list as ListView }
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
        etNew.text.toString().run {
            if (catManage) {    // Add Category
                TodoPreference.catData.add(this)
            } else {
                TodoPreference.prefData.add(TodoData(title = this))
            }
        }

        updateList()
        lstTodo.smoothScrollByOffset(lstTodo.bottom)
        TodoPreference.savePref(parent)

        etNew.text.clear()
        mode = Mode.DEFAULT
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        return true
    }

    fun updateList(manage: Boolean = catManage, state: Int = catCur) {
        if (!manage && state != catCur) {   // Show Category
            TodoPreference.catData[state].run {
                lstTodo.adapter =
                        if (state == 0) allItemsAdapter
                        else TodoAdapter(parent,
                                ArrayList(TodoPreference.prefData.filter { it.category == this }))
                parent.title = this
            }
        } else if (manage != catManage) {   // Show Category Manage
            lstTodo.adapter = categoryAdapter
            lstTodo.onItemLongClickListener = categoryLongClickListener
            parent.title = "Category Manage"
        }

        // Refresh ListView
        (lstTodo.adapter as BaseAdapter?)?.notifyDataSetChanged()
        // When manage is true, catCur set as out of index
        catCur = if (manage) TodoPreference.catData.size else state
        catManage = manage

        // After this, update category
        parent.updateCategory(catCur)
    }

}