package com.test.bottomnavigation.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.test.bottomnavigation.R
import com.test.bottomnavigation.presentation.BackPressListener
import com.test.bottomnavigation.presentation.SubScreen
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

abstract class TabFragment : Fragment(), BackPressListener {

    val cicerone: Cicerone<Router> = Cicerone.create()

    private val navigator: SupportAppNavigator by lazy {
        SupportAppNavigator(activity as FragmentActivity, childFragmentManager, R.id.subContainer)
    }

    abstract val navigationKey: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.subContainer) == null) {
            cicerone.router.replaceScreen(SubScreen(1))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        return if (isAdded) {
            val childFragment = childFragmentManager.findFragmentById(R.id.subContainer)
            childFragment is BackPressListener && childFragment.onBackPressed()
        } else {
            false
        }
    }
}