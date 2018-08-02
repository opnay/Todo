package com.opnay.todo.fragment

import android.support.v4.app.Fragment

open class BaseFragment: Fragment() {

    /*
     * @return  true    It's work. Don't super.onBackPressed on activity.
     */
    open fun onBackPressed(): Boolean { return false }

    /*
     * update Fragment's Data
     */
    open fun updateData() {}
}