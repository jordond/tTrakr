package ca.hoogit.ttrakr

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SimulationViewModel : ViewModel() {
    val TAG: String = "SimViewModel"

    val teams: MutableLiveData<List<Team>> = MutableLiveData()
    val settings: MutableLiveData<Simulation> = MutableLiveData()

    fun getPlayers(handler: (players: Map<String, List<Player>>) -> Unit) {
        Database.singleEventNoError("players", {
            val mapData = mutableMapOf<String, List<Player>>()
            it.children.mapNotNull {
                val players = it.children.mapNotNull { it.getValue(Player::class.java) }
                mapData.put(it.key, players)
            }
            handler(mapData)
        })
    }

    fun getTeams(): LiveData<List<Team>> {
        if (teams.value == null) {
            Database.singleEventNoError("teams", {
                if (it.exists()) {
                    val newTeams: List<Team> = it.children.mapNotNull { it.getValue(Team::class.java) }
                    getPlayers { players ->
                        if (players.isNotEmpty()) {
                            newTeams.forEach { team -> team.players.addAll(players[team.abbreviation]!!) }
                            teams.postValue(newTeams)
                        } else {
                            Log.e(TAG, "Whoops the players are empty...")
                        }
                    }
                }
            })
        }

        return teams
    }

    fun getSettings() : LiveData<Simulation> {
        if (settings.value == null) {
            Database.subscribe("simulation", object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {
                    Log.e(TAG, "Failed to subscript to Settings -> ${p0?.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        settings.postValue(snapshot.getValue(Simulation::class.java))
                    }
                }
            })
        }

        return settings
    }
}