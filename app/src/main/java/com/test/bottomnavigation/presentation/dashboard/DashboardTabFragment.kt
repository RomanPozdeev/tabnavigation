package com.test.bottomnavigation.presentation.dashboard

import com.test.bottomnavigation.presentation.common.TabFragment

class DashboardTabFragment : TabFragment() {
    override val navigationKey = "Dashboard"

    companion object {
        fun newInstance() = DashboardTabFragment()
    }
}