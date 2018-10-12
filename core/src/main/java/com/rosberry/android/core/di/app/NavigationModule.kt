package com.rosberry.android.core.di.app

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * @author Alexei Korshun on 12/10/2018.
 */
@Module
class NavigatorModule {

    @Singleton
    @Provides
    fun providesCicerone(): Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun provideNavigationHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder

    @Singleton
    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
}