package com.dicoding.githubuserapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuserapp.data.ThemesSetting

class FactoryVM(private val themePref: ThemesSetting) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainVM::class.java)) {
            return MainVM(themePref) as T
        }
        throw IllegalArgumentException("ViewModel class is not recognized: ${modelClass.name}")
    }
}