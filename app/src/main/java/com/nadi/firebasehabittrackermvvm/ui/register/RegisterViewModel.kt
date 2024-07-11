package com.nadi.firebasehabittrackermvvm.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.nadi.firebasehabittrackermvvm.ui.util.RegistrationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(

     var auth: FirebaseAuth,
):ViewModel() {

      var email = MutableLiveData<String>()

     var password = MutableLiveData<String>()


      var confirmPassword = MutableLiveData<String>()

    var emailPassResult = MutableLiveData<RegistrationResult>()


    fun createUserWithEmailPass() {

        auth.createUserWithEmailAndPassword(
            email.value.toString(),
            password.value.toString()
        ).addOnCompleteListener {result->

            if (result.exception?.message != null){

                val registerError = result.exception?.message.toString()
                emailPassResult.postValue(RegistrationResult(false, registerError))
                Log.d("TAG", "exception exception: "+result.exception?.message.toString())
                Log.d("TAG", "localizedMessage exception: "+result.exception?.localizedMessage.toString())
            }
            else if (result.isSuccessful) {
                emailPassResult.postValue(RegistrationResult(true, ""))

            }else {
                emailPassResult.postValue(RegistrationResult(false, ""))

                Log.d("TAG", "exception else: "+result.exception?.message.toString())
                Log.d("TAG", "localizedMessage else: "+result.exception?.localizedMessage.toString())

            }
        }
    }

}


