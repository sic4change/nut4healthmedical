package org.sic4change.nut4healthcentrotratamiento.ui.commons

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import java.util.*

import org.sic4change.nut4healthcentrotratamiento.R

class StringResourcesUtil private constructor() {

    companion object {
        private val locales: MutableList<Locale> = mutableListOf()

        fun initializeLocales(vararg localesToAdd: Locale) {
            locales.clear()
            locales.addAll(localesToAdd)
        }

        fun getLocalizedString(context: Context, locale: Locale, resId: Int): String {
            val configuration = Configuration(context.resources.configuration)
            configuration.setLocale(locale)
            val localizedContext = context.createConfigurationContext(configuration)
            return localizedContext.resources.getString(resId)
        }

        fun getAllStringsForLocales(context: Context): Map<String, List<String>> {
            val stringMap = mutableMapOf<String, MutableList<String>>()

            val fields = R.string::class.java.fields

            for (field in fields) {
                try {
                    val resId = field.getInt(null)
                    val key = context.resources.getResourceEntryName(resId)

                    val values = locales.map { locale ->
                        getLocalizedString(context, locale, resId)
                    }

                    stringMap[key] = values.toMutableList()
                } catch (e: Exception) {
                    Log.e("StringResource", "Error getting resource", e)
                }
            }

            return stringMap
        }

        fun doesStringMatchAnyLocale(context: Context, key: String, text: String): Boolean {
            val resId = context.resources.getIdentifier(key, "string", context.packageName)
            if (resId == 0) return false

            return locales.any { locale ->
                val localizedString = getLocalizedString(context, locale, resId)
                localizedString == text
            }
        }
    }
}
