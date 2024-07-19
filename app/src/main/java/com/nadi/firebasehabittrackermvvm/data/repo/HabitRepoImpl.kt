package com.nadi.firebasehabittrackermvvm.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nadi.firebasehabittrackermvvm.data.HabitModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HabitRepoImpl @Inject constructor(
    val fdb: FirebaseDatabase,
    val auth: FirebaseAuth
) {

//    fun getData(){
//        flow<Resource<HabitModel>> {  }
//
//
//
//    }



}