package petshop.workshop.com.business

import android.content.Context

interface UserPrefs {
    fun setFirstOpen()
    fun getFirstOpen(): Boolean
}

class UserPrefsImpl(context: Context, fileName: String): UserPrefs {

    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    override fun setFirstOpen() {
        prefs.edit().putBoolean(KEY_FIRST_ACTIVE, true).apply()
    }

    override fun getFirstOpen() = prefs.getBoolean(KEY_FIRST_ACTIVE, false)

    companion object {
        private const val KEY_FIRST_ACTIVE = "KEY_FIRST_ACTIVE"
    }
}