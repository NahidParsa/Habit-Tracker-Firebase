package com.nadi.firebasehabittrackermvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nadi.firebasehabittrackermvvm.R
import com.nadi.firebasehabittrackermvvm.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addNewHabit)
        }
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

//        exitDialog()
        return root
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





//    fun showAreYouSureDialog(){
//        AlertDialog.Builder(requireContext()).setMessage("Are you sure?")
//            .setPositiveButton("Yes"){dialog,which ->
////                requireActivity().finish()
////                exitProcess(0)
//                activity?.finish()
//            }
//            .setNeutralButton("No"){_,_ ->}
//            .show()
//    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}