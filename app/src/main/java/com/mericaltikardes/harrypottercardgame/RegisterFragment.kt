package com.mericaltikardes.harrypottercardgame

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //for fragment
        var loginButton = view.findViewById<Button>(R.id.loginPageLoginButton)
        //for data validation
        username = view.findViewById(R.id.registerPageEmailAddress)
        password = view.findViewById(R.id.registerPagePassword)
        confirmPassword = view.findViewById(R.id.registerPageConfirm)
        fAuth = Firebase.auth
        //for fragment
        loginButton.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginPage()
            Navigation.findNavController(it).navigate(action)
        }
        //fopr data is empty or confirmed
        var registerPageRegisterButton = view.findViewById<Button>(R.id.loginPageRegisterButton)
        registerPageRegisterButton.setOnClickListener {
            validDatas()
        }
    }

    private fun firebaseSignUp() {

        fAuth.createUserWithEmailAndPassword(
            username.text.toString(),
            password.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show()
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginPage()
                //  activity?.findNavController()?.navigate(action)
                findNavController(this).navigate(action)
                //   Navigation.findNavController(requireActivity(),R.id.navigation).navigate(action)
            } else {
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validDatas() {
        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_warning)
        if (icon != null) {
            icon.setBounds(0, 0, 150, 150)
        }//for data is null problem
        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please Enter Username", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please Enter Passsword", icon)
            }
            TextUtils.isEmpty(confirmPassword.text.toString().trim()) -> {
                confirmPassword.setError("Please Enter Confirm Password", icon)
            }
            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() &&
                    confirmPassword.text.toString().isNotEmpty() -> {
                if (username.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                    if (password.text.toString().length >= 5) {
                        if (password.text.toString() == confirmPassword.text.toString()) {
                            firebaseSignUp()

                        } else {
                            confirmPassword.setError("Password is didn't match")
                        }
                    } else {
                        password.setError("Please Enter Valid Password", icon)
                    }

                } else {
                    username.setError("Please Enter Valid E-mail Id ", icon)
                }
            }

        }
    }
}





