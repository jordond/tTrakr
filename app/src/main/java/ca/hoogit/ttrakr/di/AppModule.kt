package ca.hoogit.ttrakr.di

import android.app.Application
import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class AppModule {
    @Provides @Singleton fun provideContext(application: Application): Context = application

    @Provides @Singleton fun provideFirebase(): FirebaseDatabase = FirebaseDatabase.getInstance()
}