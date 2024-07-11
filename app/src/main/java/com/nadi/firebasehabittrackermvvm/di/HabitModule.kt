package com.nadi.firebasehabittrackermvvm.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nadiparsa.habittrackerapplication.util.SignInOutGoogle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HabitModule {

//    @Provides
//    @Singleton
//    fun provideHabitDB(application: Application): HabitDataBase =
//        Room.databaseBuilder(application,
//            HabitDataBase::class.java,
//            "habit_db")
//            .build()



    @Provides
    @Singleton
    fun provideAuthRef(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFd():FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun signInGoogle(application: Application, auth:FirebaseAuth , fdb:FirebaseDatabase):SignInOutGoogle =
        SignInOutGoogle(application,auth,fdb)



    @Provides
    @Singleton
    fun provideDbRef() =  FirebaseDatabase.getInstance().getReference("habits")
}