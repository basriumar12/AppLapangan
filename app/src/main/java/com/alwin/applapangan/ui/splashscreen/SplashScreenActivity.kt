package com.alwin.applapangan.ui.splashscreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.alwin.applapangan.MainActivity
import com.alwin.applapangan.R
import com.alwin.applapangan.ui.login.LoginActivity
import com.gorontalodigital.preference.Prefuser

class SplashScreenActivity : AppCompatActivity() {

    private val self = this
    private var sessions: SharedPreferences? = null
    private val LOADING_DURATION = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        getSupportActionBar()?.hide();
        Handler().postDelayed({
           goTO()
        }, LOADING_DURATION.toLong())
    }

    private fun goTO() {
        val intent: Intent
        val data =  Prefuser().getToken()
        if (data != null) {
            intent = Intent(self, MainActivity::class.java)
            startActivity(intent)
            self.finish()
        } else {
            intent = Intent(self, LoginActivity::class.java)
            startActivity(intent)
            self.finish()
        }
    }
}