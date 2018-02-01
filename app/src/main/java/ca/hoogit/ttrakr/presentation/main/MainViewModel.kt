package ca.hoogit.ttrakr.presentation.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import ca.hoogit.ttrakr.utils.FirebaseUtils
import ca.hoogit.ttrakr.vo.Player
import ca.hoogit.ttrakr.vo.Simulation
import ca.hoogit.ttrakr.vo.Team
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class MainViewModel @Inject constructor(val firebase: FirebaseUtils) : ViewModel() {

    val TAG: String = "SimViewModel"

    val teams: MutableLiveData<List<Team>> = MutableLiveData()
    val settings: MutableLiveData<Simulation> = MutableLiveData()

    fun getPlayers(handler: (players: Map<String, List<Player>>) -> Unit) {
        firebase.singleEventNoOnCancelled("players", {
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
            firebase.singleEventNoOnCancelled("teams", {
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

    fun getSettings(): LiveData<Simulation> {
        if (settings.value == null) {
            firebase.subscribe("simulation", object : ValueEventListener {
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