package ca.hoogit.ttrakr.di.viewModel

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module interface ViewModelModule {

    @Binds fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}