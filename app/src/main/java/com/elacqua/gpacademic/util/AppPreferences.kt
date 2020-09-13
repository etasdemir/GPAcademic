package com.elacqua.gpacademic.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor


class AppPreferences(context: Context) {
    private val _sharedPrefs: SharedPreferences
    private val _prefsEditor: Editor
    val gpaType: Int
        get() = _sharedPrefs.getInt(KEY_PREFS_GPA_TYPE, 1)

    val doNotShowAgain: Boolean
        get() = _sharedPrefs.getBoolean(KEY_PREFS_SHOW_AGAIN, false)

    fun saveGpaType(type: Int) {
        _prefsEditor.putInt(KEY_PREFS_GPA_TYPE, type)
        _prefsEditor.commit()
    }

    fun saveDoNotShowAgain(doNotShowAgain: Boolean){
        _prefsEditor.putBoolean(KEY_PREFS_SHOW_AGAIN, doNotShowAgain)
        _prefsEditor.commit()
    }

    companion object {
        const val KEY_PREFS_GPA_TYPE = "gpaType"
        const val KEY_PREFS_SHOW_AGAIN = "ShowAgain"
        private const val APP_SHARED_PREFS = "Shared Pref"
    }

    init {
        _sharedPrefs = context.getSharedPreferences(
            APP_SHARED_PREFS,
            Activity.MODE_PRIVATE
        )
        _prefsEditor = _sharedPrefs.edit()
    }
}