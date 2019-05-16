package com.test.bottomnavigation.presentation.subscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.test.bottomnavigation.R
import com.test.bottomnavigation.presentation.BackPressListener
import com.test.bottomnavigation.presentation.SubScreen
import com.test.bottomnavigation.presentation.common.TabFragment
import kotlinx.android.synthetic.main.fragment_sample.*
import ru.terrakok.cicerone.Router

class SubScreenFragment : Fragment(), BackPressListener {

    private val router: Router by lazy {
        (parentFragment as TabFragment).cicerone.router
    }

    var depth: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { depth = it.getInt(DEPTH_KEY) }
        button.text = "deep == ($depth)"
        button.setOnClickListener { navigateDeeper() }
    }

    private fun navigateDeeper() {
        router.navigateTo(SubScreen(++depth))
    }

    override fun onBackPressed(): Boolean {
        Toast.makeText(context, "backPressed $depth", Toast.LENGTH_SHORT).show()
        router.exit()
        return true
    }

    companion object {
        private const val DEPTH_KEY = "depth"

        fun newInstance(depth: Int) = SubScreenFragment().apply {
            arguments = Bundle().apply {
                putInt(DEPTH_KEY, depth)
            }
        }
    }

}