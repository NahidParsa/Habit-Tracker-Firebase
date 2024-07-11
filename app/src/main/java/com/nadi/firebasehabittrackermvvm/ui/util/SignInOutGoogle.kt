package com.nadiparsa.habittrackerapplication.util

import android.content.Context
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.nadi.firebasehabittrackermvvm.ui.util.ResultTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class SignInOutGoogle @Inject constructor(
    private val context: Context,
    private var auth: FirebaseAuth,
    private var fdb: FirebaseDatabase
) {
    var result= MutableLiveData<ResultTypes>(ResultTypes.UNKNOWN)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun googleSignIn() {

        coroutineScope.launch {

            val credentialManager = CredentialManager.create(context)
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
//            todo hide this id
                .setServerClientId("1098562648587-i3m6nkmq53a9bvcq0r4ktim7ekuh282c.apps.googleusercontent.com")
                .setNonce(createNone())
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

//            withContext(Dispatchers.IO) {
                try {
                    val request = credentialManager.getCredential(
                        request = request,
                        context = context
                    )

                    val credential = request.credential

                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)

                    val googleIdToken = googleIdTokenCredential.idToken
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, googleIdToken, Toast.LENGTH_SHORT).show()

                    }
                    val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

                    if (!request.credential.data.isEmpty) {
                        auth.signInWithCredential(authCredential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {

                                    val currentUser = auth.currentUser
                                    putAuth(currentUser, fdb)
                                    result.postValue(ResultTypes.SUCCESS)
                                } else {
                                    result.postValue(ResultTypes.ERROR)
                                }
                            }
                    }
                } catch (e: GetCredentialException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: GoogleIdTokenParsingException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

//    }

    fun createNone():String{

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashNonce =
            digest.fold("") { str, it ->
                str + "%02x".format(it)
            }
        return hashNonce
    }

    fun putAuth(currentUser:  FirebaseUser?, fdb: FirebaseDatabase){
        currentUser?.uid?.let { uid ->
            fdb.reference.child("users").child(
                uid
            )
        }?.setValue( UserData( currentUser?.uid, currentUser?.displayName , currentUser?.photoUrl.toString() ))
    }
}

