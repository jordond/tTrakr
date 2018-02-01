package ca.hoogit.ttrakr.di

import ca.hoogit.ttrakr.di.activityModule.MainActivityModule
import ca.hoogit.ttrakr.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity
}