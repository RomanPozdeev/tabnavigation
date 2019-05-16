package com.test.bottomnavigation.presentation.home

import com.test.bottomnavigation.presentation.DashboardScreen
import com.test.bottomnavigation.presentation.HomeScreen
import com.test.bottomnavigation.presentation.common.TabFragment

class HomeTabFragment: TabFragment() {
    override val navigationKey = "Home"

    companion object {
        fun newInstance() = HomeTabFragment()
    }
}