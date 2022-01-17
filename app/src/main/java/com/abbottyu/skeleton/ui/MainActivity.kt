package com.abbottyu.skeleton.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import com.abbottyu.skeleton.base.BaseActivity
import com.abbottyu.skeleton.ui.home.HomeActivity
import com.abbottyu.skeleton.ui.theme.SkeletonTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkeletonTheme {
                Button(onClick = {
                    startActivity(Intent(this, HomeActivity::class.java))
                }) {
                    Text("HomeActivity")
                }
            }
        }
    }
}