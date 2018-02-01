package ca.hoogit.ttrakr.di.activityModule

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import ca.hoogit.ttrakr.di.viewModel.ViewModelKey
import ca.hoogit.ttrakr.presentation.main.MainActivity
import ca.hoogit.ttrakr.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {

    @Binds fun providesAppCompatActivity(mainActivity: MainActivity): AppCompatActivity

    @Binds @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
            mainViewModel: MainViewModel
    ): ViewModel
}