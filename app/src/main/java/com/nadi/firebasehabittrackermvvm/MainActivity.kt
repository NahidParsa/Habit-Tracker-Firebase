package com.nadi.firebasehabittrackermvvm

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.nadi.firebasehabittrackermvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment ||
                destination.id == R.id.registerFragment
            ) {
                navView.visibility = View.GONE

                supportActionBar?.hide()

            } else {
                navView.visibility = View.VISIBLE
                supportActionBar?.show()
            }
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)






        try {
            val user = FirebaseAuth.getInstance().currentUser
            if (!user?.uid.isNullOrEmpty()){
                Log.d("TAG", "setStartDestination if username: "+user?.displayName+user?.uid)
                navController.navigate(R.id.navigation_home)

            } else {
                Log.d("TAG", "setStartDestination else username: "+user?.displayName)
                navController.navigate(R.id.loginFragment)

            }
        }catch (e: Exception){
            Log.d("TAG", "setStartDestination exception: "+e.localizedMessage)
        }
    }

}

