package com.nadi.firebasehabittrackermvvm.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.nadi.firebasehabittrackermvvm.R
import com.nadi.firebasehabittrackermvvm.databinding.BottomAddNewHabitBinding
import com.nadi.firebasehabittrackermvvm.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    var _bindingBottom : BottomAddNewHabitBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val bindingBottom get() = _bindingBottom!!

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addNewHabit)
        }
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }




        binding.fab.setOnClickListener {

            this.view?.let {
                showAddNewHabit(it)
            }



        }
        exitDialog()
        return root
    }

    private fun showAddNewHabit(view: View) {
        val inflater = LayoutInflater.from(requireContext())
        _bindingBottom = BottomAddNewHabitBinding.inflate(inflater)

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)



        bottomSheetDialog.setContentView(bindingBottom.root)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

//        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.show()




        getData()


    }

    private fun getData() {
        bindingBottom.btnAdd.setOnClickListener {

            val title = bindingBottom.edtTitle.text.toString().trim()
            val description = bindingBottom.edtDescription.text.toString().trim()
            val date = bindingBottom.btnPickDate.text.toString()
            val time = bindingBottom.btnPickTime.text.toString()


            viewModel.title.value = title
            viewModel.description.value = description
            viewModel.date.value = date
            viewModel.time.value = time

            viewModel.writeData()
        }
    }








    private fun exitDialog() {
        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
//                if (!viewModel.showedAlert){
                activity?.finish()
//                    showAreYouSureDialog()
//                    viewModel.showedAlert = true
//                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, // LifecycleOwner
            callback
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
//        _bindingBottom = null

    }
}