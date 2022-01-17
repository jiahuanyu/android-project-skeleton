package com.abbottyu.skeleton.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.abbottyu.skeleton.base.BaseActivity
import com.abbottyu.skeleton.ui.theme.SkeletonTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkeletonTheme {
                ProvideWindowInsets {
                    HomePage()
                }
            }
        }
        viewModel.uiEffect.observe(this, { effect ->
            if (effect is HomePageUIEffect.ShowToastEffect) {
                Toast.makeText(this, effect.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }
}