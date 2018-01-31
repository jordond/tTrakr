package ca.hoogit.ttrakr.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideFirebase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun providePrefs(application: Application): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(application)

}
