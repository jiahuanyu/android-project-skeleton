package com.abbottyu.skeleton.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout

open class BaseFragment : Fragment() {
    private var toolbar: Toolbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configToolbar(view)
    }

    private fun configToolbar(contentView: View) {
        val toolbarId = resources.getIdentifier("id_toolbar", "id", requireContext().packageName)
        if (toolbarId > 0) {
            val toolbar: Toolbar = contentView.findViewById(toolbarId) ?: return

            toolbar.setOnMenuItemClickListener { menuItem ->
                onOptionsItemSelected(menuItem)
            }

            val menuResId = getMenuResourceId()
            if (menuResId > 0) {
                toolbar.inflateMenu(menuResId)
            }

            toolbar.title = getTitle()
            this.toolbar = toolbar
        }
        val appbarId = resources.getIdentifier("id_appbar", "id", requireContext().packageName)
        if (appbarId > 0) {
            val appbar: AppBarLayout = contentView.findViewById(appbarId) ?: return
            appbar.setOnApplyWindowInsetsListener { view, insets ->
                val inset = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insets.getInsets(WindowInsets.Type.statusBars()).top
                } else {
                    insets.systemWindowInsetTop
                }
                view.updatePadding(top = inset)
                insets
            }
            ViewCompat.requestApplyInsets(appbar)
        }
    }

    protected fun getToolbar(): Toolbar {
        return toolbar!!
    }

    protected open fun getTitle(): String {
        return ""
    }

    protected open fun getMenuResourceId(): Int {
        return -1
    }
}
