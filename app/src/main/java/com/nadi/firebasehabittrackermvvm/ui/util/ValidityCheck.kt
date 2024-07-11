package com.nadi.firebasehabittrackermvvm.ui.util

class ValidityCheck {

     fun checkValidityLogin(email: String, password: String): Boolean {

        return  ( email == null || password == null ||
                email.isEmpty() || password.isEmpty() ||
                password.length <6

                )
    }

    fun checkValidityRegister(email: String, password: String, confirmPassword: String):Boolean {

        return (email == null || password == null || confirmPassword == null
                || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || password != confirmPassword || password.length < 6
                )
    }

}