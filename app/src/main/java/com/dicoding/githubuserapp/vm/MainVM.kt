package com.dicoding.githubuserapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuserapp.data.ThemesSetting
import kotlinx.coroutines.launch

class MainVM(private val themePref: ThemesSetting) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return themePref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isNightThemeOn: Boolean) {
        viewModelScope.launch {
            themePref.saveThemeSetting(isNightThemeOn)
        }
    }
}