package com.example.myapplication.di

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.DaggerDemoApplication
import com.example.myapplication.di.component.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {

    fun init(application: DaggerDemoApplication) {
        DaggerAppComponent.builder().application(application).build().inject(application)
    }

    private fun handleActivity(activity: AppCompatActivity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
    }
}