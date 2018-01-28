package ca.hoogit.ttrakr

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.util.*

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
            if (teams != null) {
                teams?.forEach { Log.d(TAG, "Team: ${it.name}") }
                teamscount.text = "TEAM SIZE: ${teams.size}"
            }
        })

        simViewModel.getSettings().observe(this, Observer { settings ->
            if (settings != null) {
                updated.text = "updated at: ${DateFormat.getDateTimeInstance().format(Date())}"
                started.text = "started: ${settings.started}"
                start.text = "start time: ${settings.start}"
                factor.text = "speed factor: ${settings.factor}"
                chance.text = "chance: ${settings.chance}"
                maxGames.text = "max games: ${settings.maxGames}"
            }
        })
    }
}
