package ca.hoogit.ttrakr.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ca.hoogit.ttrakr.SimulationViewModel
import ca.hoogit.ttrakr.utils.ViewModelFactor
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
internal abstract class ViewModelFactory {
    @Binds
    @IntoMap
    @ViewModelKey(SimulationViewModel::class)
    internal abstract fun bindSimViewModel(simViewModel: SimulationViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactor): ViewModelProvider.Factory
}