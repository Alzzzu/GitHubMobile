package my.first.github.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context)  {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(KeyValueStorage.KEY_PREFERENCES_NAME, Context.MODE_PRIVATE)
    fun putString(key: String?, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}