package ca.hoogit.ttrakr.presentation

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), HasFragmentInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

}