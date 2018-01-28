package ca.hoogit.ttrakr

import android.util.Log
import com.google.firebase.database.*

object Database {
    val TAG = "Database"

    fun ref(path: String): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(path);
    }

    fun singleEvent(path: String, handler: ValueEventListener) {
        ref(path).addListenerForSingleValueEvent(handler)
    }

    fun singleEventNoError(path: String, handler: (snapshot: DataSnapshot) -> Unit) {
        ref(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                handler(p0)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e(TAG, p0.message)
            }
        })
    }

    fun subscribe(path: String, handler: ValueEventListener) {
        ref(path).addValueEventListener(handler)
    }
}
