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

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    protected val navigator: Navigator by lazy {

        object : SupportAppNavigator(this.activity, childFragmentManager, R.id.container) {

            override fun activityBack() {
                onExit()
            }
        }
    }

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

    open fun onExit() {}
}
