/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.core.ui

import com.rosberry.android.core.R
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

/**
 * @author Alexei Korshun on 12/10/2018.
 */
abstract class FlowFragment : AppFragment() {

    override val layoutRes: Int = R.layout.l_container

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.container) as? AppFragment

    abstract var navigatorHolder: NavigatorHolder

    abstract val navigator: Navigator

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        return currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    abstract fun closeScope()

    open fun onExit() {}
}
