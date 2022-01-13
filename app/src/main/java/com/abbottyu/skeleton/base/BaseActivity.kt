package com.abbottyu.skeleton.base

import android.graphics.Color
import android.os.Build
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding
import com.google.android.material.appbar.AppBarLayout

class BaseActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setWindowAttribute()
        configToolbar()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        setWindowAttribute()
        configToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            setWindowAttribute()
        }
    }

    private fun setWindowAttribute() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
    }

    /**
     * 将布局中的 Toolbar 设置到 SupportActionBar 中
     */
    private fun configToolbar() {
        val toolbarId = resources.getIdentifier("id_toolbar", "id", packageName)
        if (toolbarId > 0) {
            val toolbar: Toolbar = findViewById(toolbarId) ?: return
            setSupportActionBar(toolbar)
            this.toolbar = toolbar
        }
        val appbarId = resources.getIdentifier("id_appbar", "id", packageName)
        if(appbarId > 0) {
            val appbar: AppBarLayout = findViewById(appbarId) ?: return
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
}
