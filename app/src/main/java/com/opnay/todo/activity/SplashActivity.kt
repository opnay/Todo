package com.opnay.todo.activity

import android.app.Activity
import android.os.Bundle
import com.opnay.todo.R
import com.opnay.todo.Util
import com.opnay.todo.preference.TodoPreference
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        TodoPreference.loadPref(this)

        Timer().schedule(2000) {
            Util.startActivity(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.finish()
        }
    }
}
