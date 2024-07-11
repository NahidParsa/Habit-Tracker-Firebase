package com.nadi.firebasehabittrackermvvm.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nadi.firebasehabittrackermvvm.R
import com.nadi.firebasehabittrackermvvm.databinding.FragmentRegisterBinding
import com.nadi.firebasehabittrackermvvm.ui.util.ResultTypes
import com.nadi.firebasehabittrackermvvm.ui.util.ValidityCheck
import com.nadiparsa.habittrackerapplication.util.SignInOutGoogle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<RegisterViewModel>()
    private val validityCheck = ValidityCheck()

    @Inject
    lateinit var signIn : SignInOutGoogle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val signIn = SignInOutGoogle(requireContext())
//        val auth = FirebaseAuth.getInstance()
//        val fdb = FirebaseDatabase.getInstance()

        binding.signInGoogle.setOnClickListener {
//            signIn.googleSignIn(auth, fdb)
            signIn.googleSignIn()

            signIn.result.observe(viewLifecycleOwner, Observer {
                Log.d("TAG", "onCreateView: "+signIn.result.value.toString())

                if (it == ResultTypes.SUCCESS){

                    if(findNavController().currentDestination?.id==R.id.registerFragment){
                        findNavController().navigate(R.id.action_registerFragment_to_navigation_home)
                    }
                }
                if (it == ResultTypes.ERROR){
                    Toast.makeText(requireContext(), "something went wrong!!", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.btnRegisterEmail.setOnClickListener {

            val email= binding.edtEmail.text.toString().trim()
            val password= binding.edtPassword.text.toString().trim()
            val confirmPassword = binding.edtConfirmPassword.text.toString().trim()

            val validity = validityCheck.checkValidityRegister(email,password,confirmPassword)

            viewmodel.email.value = email
            viewmodel.password.value = password
            viewmodel.confirmPassword.value = confirmPassword

            Log.d("TAG", "onCreateView: ${viewmodel.email.value}")
            if ( !validity) {

//                viewmodel.createUserWithEmailPass(auth)
                viewmodel.createUserWithEmailPass()

                viewmodel.emailPassResult.observe(viewLifecycleOwner, Observer { result ->
                    if (result.isRegistered == true){
                        if(findNavController().currentDestination?.id==R.id.registerFragment){
                            findNavController().navigate(R.id.action_registerFragment_to_navigation_home)}
                    }
                    else if (!result.error.isNullOrEmpty()){
                        Toast.makeText(requireContext(), result.error.toString(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(requireContext(), "error in registretion isNullOrEmpty" +result.error.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "error in registretion: isNullOrEmpty "+result.error.toString())

                    }
                    else{
                        Toast.makeText(requireContext(), "email else", Toast.LENGTH_SHORT).show()
                        Toast.makeText(requireContext(), "error in registretion else" +result.error.toString(), Toast.LENGTH_SHORT).show()

                        Log.d("TAG", "error in registretion else: "+result.error.toString())
                    }
                })
            }else{
                Toast.makeText(requireContext(), "email or password is not valid", Toast.LENGTH_SHORT).show()

            }

        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}