package com.opnay.todo.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.opnay.todo.R
import com.opnay.todo.activity.MainActivity
import com.opnay.todo.adapter.CategoryAdapter
import com.opnay.todo.data.Category
import com.opnay.todo.sqlite.db
import kotlinx.android.synthetic.main.fragment_category.view.*

class CategoryFragment: BaseFragment() {

    private val parent: MainActivity by lazy { activity!! as MainActivity }

    // holder
    private var rootView: View? = null
    private val pagerCategory: ViewPager by lazy { rootView!!.categoryPager }
    private val linearPager: LinearLayout by lazy { rootView!!.pagerNav }
    private val imagePager: ArrayList<ImageView> = ArrayList()
    var pagerPosition: Int = 0
        set(v) {
            if (v > dataCategory.size + 1)
                throw IndexOutOfBoundsException()
            else {
                field = v
                imagePager.forEachIndexed { i, view -> view.isEnabled = (v == i) }
            }
        }

    private val dataCategory: ArrayList<Category> by lazy { ArrayList(parent.db.category) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_category, container, false)

        pagerCategory.offscreenPageLimit = 5
        pagerCategory.adapter = CategoryAdapter(parent, fragmentManager!!, dataCategory)
        pagerCategory.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPixel: Int) {}
            override fun onPageSelected(pos: Int) { pagerPosition = pos }
        })

        // Create Circle
        // margin: start, end 8
        // padding: all 8
        for (i: Int in 0 .. dataCategory.size) {
            imagePager.add(createNavIcon(R.drawable.circle_12dp,
                    LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                            .apply { setMargins(8, 0, 8, 0) })
            )
        }

        pagerPosition = 0

        return rootView
    }

    override fun updateData() {
        // Load Category from db
        dataCategory.clear()
        dataCategory.addAll(parent.db.category)
        pagerCategory.adapter!!.notifyDataSetChanged()
    }

    private fun createNavIcon(id: Int, params: LinearLayout.LayoutParams): ImageView =
            ImageView(parent).apply {
                setImageDrawable(ContextCompat.getDrawable(context, id))
                setPadding(8, 8, 8, 8)
                isEnabled = false
                linearPager.addView(this, params)
            }
}