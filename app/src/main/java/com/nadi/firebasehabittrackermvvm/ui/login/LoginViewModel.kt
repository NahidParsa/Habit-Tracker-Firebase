package com.nadi.firebasehabittrackermvvm.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nadi.firebasehabittrackermvvm.ui.util.ResultTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {


    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val result = MutableLiveData<ResultTypes>(ResultTypes.UNKNOWN)



    fun signInEmailPassword(){

        viewModelScope.launch {

            auth.signInWithEmailAndPassword(email.value.toString(), password.value.toString()).addOnCompleteListener {
                if (it.isSuccessful) {
                    result.postValue(ResultTypes.SUCCESS)

                } else {
                    result.postValue(ResultTypes.ERROR)
                }
            }

        }
    }


}