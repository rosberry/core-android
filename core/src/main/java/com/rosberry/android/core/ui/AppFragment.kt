/*
 * Copyright (c) 2018 Rosberry. All rights reserved.
 */
package com.rosberry.android.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment

/**
 * @author Alexei Korshun on 08/10/2018.
 */
abstract class AppFragment : MvpAppCompatFragment() {

    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)

    open fun onBackPressed() = false

}
