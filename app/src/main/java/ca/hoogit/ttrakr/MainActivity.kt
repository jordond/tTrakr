package ca.hoogit.ttrakr

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    @Suppress("PropertyName")
    val TAG = "Main"


    lateinit var simViewModel: SimulationViewModel;
    val teams: MutableList<Team> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simViewModel = ViewModelProviders.of(this).get(SimulationViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        simViewModel.getTeams().observe(this, Observer { teams ->
            Log.i(TAG, "Found some stuff...");
            teams?.forEach { Log.d(TAG, "Team: ${it.name}") }
        })
    }
}
