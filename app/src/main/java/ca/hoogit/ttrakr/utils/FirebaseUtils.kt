package ca.hoogit.ttrakr.utils

import android.util.Log
import com.google.firebase.database.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUtils @Inject constructor(val firebase: FirebaseDatabase) {
    val TAG = "Firebase"

    private fun ref(path: String): DatabaseReference {
        return firebase.getReference(path)
    }

    fun singleEvent(path: String, handler: ValueEventListener) {
        ref(path).addListenerForSingleValueEvent(handler)
    }

    fun singleEventNoOnCancelled(path: String, handler: (snapshot: DataSnapshot) -> Unit) {
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
