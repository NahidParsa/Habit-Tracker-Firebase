package com.nadi.firebasehabittrackermvvm.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nadi.firebasehabittrackermvvm.R
import com.nadi.firebasehabittrackermvvm.databinding.FragmentLoginBinding
import com.nadi.firebasehabittrackermvvm.databinding.FragmentNotificationsBinding
import com.nadi.firebasehabittrackermvvm.ui.util.ResultTypes
import com.nadi.firebasehabittrackermvvm.ui.util.ValidityCheck
import com.nadiparsa.habittrackerapplication.util.SignInOutGoogle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment(){

    private var _binding: FragmentLoginBinding? = null


    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()
    private val validityCheck = ValidityCheck()
    @Inject
    lateinit var signInGoogle : SignInOutGoogle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnSignInEmail.setOnClickListener {

            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            viewModel.email.value = email
            viewModel.password.value = password

            val validity: Boolean = validityCheck.checkValidityLogin(
                viewModel.email.value.toString(),
                viewModel.password.value.toString()
            )


            if (validity) {

                Toast.makeText(
                    requireContext(),
                    "email or password are required!!",
                    Toast.LENGTH_SHORT
                ).show()
                Toast.makeText(
                    requireContext(),
                    "password should be more than 6 letters!!",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                viewModel.signInEmailPassword()
                viewModel.result.observe(viewLifecycleOwner, Observer {

                    if (it == ResultTypes.SUCCESS) {

                        Log.d("TAG", "signInEmailPassword: " + it.toString())
                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            findNavController().navigate((R.id.action_loginFragment_to_navigation_home))

                        }
                    } else if (it == ResultTypes.ERROR) {
                        Log.d("TAG", "signInEmailPassword: " + it.toString())

                        Toast.makeText(
                            requireContext(),
                            "wrong email or password!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

        }
//        }

        binding.tvNoAAccount.setOnClickListener {
            if(findNavController().currentDestination?.id==R.id.loginFragment) {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
        binding.signInGoogle.setOnClickListener {
//            googleSignIn(auth, fdb)
//            signInGoogle.googleSignIn(auth, fdb)
            signInGoogle.googleSignIn()

            signInGoogle.result.observe(viewLifecycleOwner, Observer {
                if (it == ResultTypes.SUCCESS){
                    if(findNavController().currentDestination?.id==R.id.loginFragment) {
                        findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                    }
                }
                if (it == ResultTypes.ERROR){
                    Toast.makeText(requireContext(), "something went wrong!!", Toast.LENGTH_SHORT).show()
                }
            })

        }
        return root
    }




    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

