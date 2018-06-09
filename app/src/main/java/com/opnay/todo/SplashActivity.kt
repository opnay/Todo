package com.opnay.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(2000) {
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(i)
            this@SplashActivity.finish()
        }
    }
}
