package ca.hoogit.ttrakr

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object Database {
    val TAG = "Database"

    fun singleEvent(path: String, handler: ValueEventListener) {
        FirebaseDatabase.getInstance().getReference(path).addListenerForSingleValueEvent(handler)
    }

    fun singleEventNoError(path: String, handler: (snapshot: DataSnapshot) -> Unit) {
        FirebaseDatabase.getInstance().getReference(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                handler(p0)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e(TAG, p0.message)
            }
        })
    }
}
