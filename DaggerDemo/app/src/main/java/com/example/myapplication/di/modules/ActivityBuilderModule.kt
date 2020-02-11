package com.example.myapplication.di.modules

import com.example.myapplication.view.DisplayTrendingRepoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeDisplayActivity(): DisplayTrendingRepoActivity
}