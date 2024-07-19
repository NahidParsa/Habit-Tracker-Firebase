package com.nadi.firebasehabittrackermvvm.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nadi.firebasehabittrackermvvm.data.HabitModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeViewModel @Inject constructor(
    val fdb: FirebaseDatabase,
    val auth: FirebaseAuth
): ViewModel() {

    var title = MutableLiveData<String>("")
    var time = MutableLiveData<String>("")
    var date = MutableLiveData<String>("")
    var description = MutableLiveData<String>("")


    init {

        fetchDataFromFB()
    }

    private fun fetchDataFromFB() {

    }


    fun writeData(){
        val habit = HabitModel(
//            auth.currentUser?.uid.toString(),
            title.value.toString(),
            description.value.toString(),
            date.value.toString(),
            time.value.toString())



//        fdb.reference.child("habits")
        fdb.getReference("Habits")
            .child(auth.currentUser?.uid.toString())
            .push()
            .setValue(habit)
            .addOnCompleteListener {
                Log.d("TAG", "writeData: data Succeed")

            }.addOnFailureListener {
                 Log.d("TAG", "writeData: data failed")
             }




    }








    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}