package com.example.myapplication.di.component

import android.app.Application
import com.example.myapplication.DaggerDemoApplication
import com.example.myapplication.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class,
        DbModule::class,
        NetworkModule::class/*,
        ServiceModule::class*/])

interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder

        fun build(): AppComponent
    }

    fun inject(application: DaggerDemoApplication)
}