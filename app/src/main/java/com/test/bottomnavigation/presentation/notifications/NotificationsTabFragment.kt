package com.test.bottomnavigation.presentation.notifications

import com.test.bottomnavigation.presentation.common.TabFragment

class NotificationsTabFragment : TabFragment() {
    override val navigationKey = "Notifications"

    companion object {
        fun newInstance() = NotificationsTabFragment()
    }
}