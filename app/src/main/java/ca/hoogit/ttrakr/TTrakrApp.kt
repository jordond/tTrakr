package ca.hoogit.ttrakr

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import ca.hoogit.ttrakr.di.AppComponent
import ca.hoogit.ttrakr.di.DaggerAppComponent

class TTrakrApp : Application() {

    @set:VisibleForTesting
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().application(this).build()
    }
}

val Context.component: AppComponent
    get() = (applicationContext as TTrakrApp).component

val Fragment.component: AppComponent
    get() = activity!!.component