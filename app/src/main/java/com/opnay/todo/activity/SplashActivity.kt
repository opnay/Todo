package com.opnay.todo.activity

import android.app.Activity
import android.os.Bundle
import com.opnay.todo.R
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(2000) {
            startActivity<MainActivity>()
            this@SplashActivity.finish()
        }
    }
}
