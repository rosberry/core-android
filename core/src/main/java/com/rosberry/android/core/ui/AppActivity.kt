/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */
package com.rosberry.android.core.ui

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

/**
 * @author Alexei Korshun on 08/10/2018.
 */
abstract class AppActivity : MvpAppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    protected abstract val layoutRes: Int
    protected abstract val navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {

        val fragmentList = supportFragmentManager.fragments

        var handled = false
        for (fragment in fragmentList) {
            if (fragment is AppFragment) {
                handled = fragment.onBackPressed()
                if (handled) break
            }
        }

        if (!handled) {
            super.onBackPressed()
        }
    }
}
