package com.abbottyu.skeleton.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.abbottyu.skeleton.base.BaseActivity
import com.abbottyu.skeleton.ui.theme.SkeletonTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkeletonTheme {
                ProvideWindowInsets {
                    HomePage()
                }
            }
        }
    }
}