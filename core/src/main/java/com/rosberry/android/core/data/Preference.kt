package com.rosberry.android.core.data

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegate for [android.content.SharedPreferences]
 *
 *
 * @param context - Context for creating [SharedPreferences]
 * @param key - function that provides a name for [SharedPreferences]
 * @param mode - Operating mode. Value is either 0 or a combination of [Context.MODE_PRIVATE],
 *   [Context.MODE_WORLD_READABLE], [Context.MODE_WORLD_WRITEABLE], and [Context.MODE_MULTI_PROCESS]
 * @author Alexei Korshun on 26.07.2020.
 */
class Preference constructor(
        private val context: Context,
        private val key: (KProperty<*>) -> String = KProperty<*>::name,
        private val mode: Int = Context.MODE_PRIVATE
) : ReadOnlyProperty<Any, SharedPreferences> {

    override fun getValue(thisRef: Any, property: KProperty<*>): SharedPreferences =
            context.getSharedPreferences(key(property), mode)
}