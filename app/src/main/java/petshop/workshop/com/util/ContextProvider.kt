package petshop.workshop.com.util

import android.content.Context
import android.content.Intent
import android.os.Bundle

interface ContextProvider {
    fun startActivity(intent: Intent)

    fun startActivity(intent: Intent, bundle: Bundle)

    fun getString(resId: Int): String

    fun getString(resId: Int, vararg objects: Any): String

    fun getStringArray(resId: Int): Array<String>

    fun getContext(): Context
}

class SimpleContextProvider(private val context: Context) : ContextProvider {
    override fun getStringArray(resId: Int): Array<String> = context.resources.getStringArray(resId)

    override fun startActivity(intent: Intent) = context.startActivity(intent)

    override fun startActivity(intent: Intent, bundle: Bundle) = context.startActivity(intent, bundle)

    override fun getString(resId: Int): String = context.getString(resId)

    override fun getString(resId: Int, vararg objects: Any): String = context.getString(resId, *objects)

    override fun getContext(): Context = context
}