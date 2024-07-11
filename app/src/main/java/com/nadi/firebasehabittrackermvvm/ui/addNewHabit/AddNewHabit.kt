package com.nadi.firebasehabittrackermvvm.ui.addNewHabit

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nadi.firebasehabittrackermvvm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewHabit : Fragment() {

    companion object {
        fun newInstance() = AddNewHabit()
    }

    private val viewModel: AddNewHabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_new_habit, container, false)
    }
}