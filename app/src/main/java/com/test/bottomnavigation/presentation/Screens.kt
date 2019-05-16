package com.test.bottomnavigation.presentation

import androidx.fragment.app.Fragment
import com.test.bottomnavigation.presentation.dashboard.DashboardTabFragment
import com.test.bottomnavigation.presentation.home.HomeTabFragment
import com.test.bottomnavigation.presentation.notifications.NotificationsTabFragment
import com.test.bottomnavigation.presentation.subscreen.SubScreenFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class DashboardScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return DashboardTabFragment.newInstance()
    }
}

class HomeScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return HomeTabFragment.newInstance()
    }
}

class NotificationsScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return NotificationsTabFragment.newInstance()
    }
}

class SubScreen(private val counter: Int): SupportAppScreen() {
    override fun getFragment(): Fragment {
        return SubScreenFragment.newInstance(counter)
    }
}