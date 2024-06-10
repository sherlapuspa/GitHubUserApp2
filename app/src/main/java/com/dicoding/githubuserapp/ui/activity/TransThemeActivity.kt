package com.dicoding.githubuserapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.ThemesSetting
import com.dicoding.githubuserapp.databinding.ActivityTransThemeBinding
import com.dicoding.githubuserapp.vm.FactoryVM
import com.dicoding.githubuserapp.vm.MainVM

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class TransThemeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var bind: ActivityTransThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTransThemeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnPrev.setOnClickListener(this)

        val themePref = ThemesSetting.getInstance(dataStore)
        val mainVM = ViewModelProvider(this, FactoryVM(themePref)).get(
            MainVM::class.java
        )
        mainVM.getThemeSettings().observe(this) { isNightThemeOn: Boolean ->
            if (isNightThemeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                bind.changeTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                bind.changeTheme.isChecked = false
            }
        }

        bind.changeTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainVM.saveThemeSetting(isChecked)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_prev -> {
                val toPrev = Intent(this@TransThemeActivity, MainActivity::class.java)
                startActivity(toPrev)
                finish()
            }
        }
    }
}