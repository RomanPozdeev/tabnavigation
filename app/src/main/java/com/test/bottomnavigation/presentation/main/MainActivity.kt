package com.test.bottomnavigation.presentation.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.test.bottomnavigation.R
import com.test.bottomnavigation.presentation.DashboardScreen
import com.test.bottomnavigation.presentation.HomeScreen
import com.test.bottomnavigation.presentation.NotificationsScreen
import com.test.bottomnavigation.presentation.common.TabFragment
import com.test.bottomnavigation.presentation.dashboard.DashboardTabFragment
import com.test.bottomnavigation.presentation.home.HomeTabFragment
import com.test.bottomnavigation.presentation.notifications.NotificationsTabFragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace

class MainActivity : FragmentActivity() {

    private val homeTabFragment: HomeTabFragment by lazy {
        supportFragmentManager.findFragmentByTag(HomeScreen().screenKey)
                as? HomeTabFragment ?: HomeScreen().fragment as HomeTabFragment
    }
    private val dashboardTabFragment: DashboardTabFragment by lazy {
        supportFragmentManager.findFragmentByTag(DashboardScreen().screenKey)
                as? DashboardTabFragment ?: DashboardScreen().fragment as DashboardTabFragment
    }
    private val notificationsTabFragment: NotificationsTabFragment by lazy {
        supportFragmentManager.findFragmentByTag(NotificationsScreen().screenKey)
                as? NotificationsTabFragment ?: NotificationsScreen().fragment as NotificationsTabFragment
    }

    private var currentTab: String = HomeScreen().screenKey
    private val localCicerone = Cicerone.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationBar()

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.navigation_home
        } else {
            savedInstanceState.getString(currentTabKey)?.let { currentTab = it }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(currentTabKey, currentTab)
    }

    private fun setupNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> localCicerone.router.replaceScreen(HomeScreen())
                R.id.navigation_dashboard -> localCicerone.router.replaceScreen(DashboardScreen())
                R.id.navigation_notifications -> localCicerone.router.replaceScreen(NotificationsScreen())
            }
            true
        }
    }

    private val navigator = object : Navigator {
        override fun applyCommands(commands: Array<Command>) {
            for (command in commands) applyCommand(command)
        }

        private fun applyCommand(command: Command) {
            when (command) {
                is Back -> finish()
                is Replace -> {
                    when (command.screen) {
                        is HomeScreen -> changeTab(homeTabFragment)
                        is DashboardScreen -> changeTab(dashboardTabFragment)
                        is NotificationsScreen -> changeTab(notificationsTabFragment)
                    }
                }
            }
        }

        //attach() / detach() vs show() / hide() - надо поисследовать.
        private fun changeTab(targetFragment: TabFragment) {
            with(supportFragmentManager.beginTransaction()) {

                supportFragmentManager.fragments
                        .filter { it != targetFragment }
                        .forEach {
                            hide(it)
                            it.userVisibleHint = false
                        }

                targetFragment.let {
                    currentTab = it.navigationKey
                    if (it.isAdded) {
                        show(it)
                    } else {
                        add(R.id.flowContainer, it, it.navigationKey)
                    }
                    it.userVisibleHint = true
                }
                commit()
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        localCicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        localCicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentByTag(currentTab) as TabFragment
        if (!currentFragment.onBackPressed()) {
            finish()
        }
    }

    companion object {
        private const val currentTabKey = "CURRENT_TAB"
    }
}
