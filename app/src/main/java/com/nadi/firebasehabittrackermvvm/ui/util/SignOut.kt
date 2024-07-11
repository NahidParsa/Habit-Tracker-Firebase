package com.nadi.firebasehabittrackermvvm.ui.util

import com.google.firebase.auth.FirebaseAuth
import com.nadiparsa.habittrackerapplication.util.UserData
import kotlin.coroutines.cancellation.CancellationException


class SignOut{

    suspend fun signOut(auth: FirebaseAuth) {
        try {
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(auth: FirebaseAuth): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }
}