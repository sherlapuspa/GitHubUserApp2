package com.dicoding.githubuserapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.ThemesSetting
import com.dicoding.githubuserapp.databinding.ActivitySplashScreenBinding
import com.dicoding.githubuserapp.databinding.ActivityTransThemeBinding
import com.dicoding.githubuserapp.vm.FactoryVM
import com.dicoding.githubuserapp.vm.MainVM

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var bind: ActivitySplashScreenBinding
    private lateinit var bindSetting: ActivityTransThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, 2000)

        getThemeSettings()
    }

    private fun getThemeSettings() {
        bindSetting = ActivityTransThemeBinding.inflate(layoutInflater)

        val themePref = ThemesSetting.getInstance(dataStore)
        val mainVM = ViewModelProvider(this, FactoryVM(themePref)).get(
            MainVM::class.java
        )

        mainVM.getThemeSettings().observe(this, { isNightThemeOn: Boolean ->
            if (isNightThemeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                bindSetting.changeTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                bindSetting.changeTheme.isChecked = false
            }
        })
    }
}