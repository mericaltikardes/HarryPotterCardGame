package com.mericaltikardes.harrypottercardgame

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginPage : Fragment() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //for data valid
        val loginButton = view.findViewById<Button>(R.id.loginPageLoginButton)
        username = view.findViewById(R.id.loginPageEmailAddress)
        password = view.findViewById(R.id.loginPagePassword)
        fAuth = Firebase.auth
        loginButton.setOnClickListener {
            validDatas()
        }

        //For register Fragment
        val registerBtn = view.findViewById<Button>(R.id.loginPageRegisterButton)
        registerBtn.setOnClickListener {
            val action = LoginPageDirections.actionLoginPageToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }
        val forgotPassword = view.findViewById<TextView>(R.id.textViewForgotPasssword)
        forgotPassword.setOnClickListener {
            val intent = Intent(this.activity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validDatas() {
        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_warning)
        if (icon != null) {
            icon.setBounds(0, 0, 150, 150)
        }
        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please Enter Username", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please Enter Password", icon)
            }
            username.text.toString().isNotEmpty() &&
                    password.text.toString().isNotEmpty() -> {
                if (username.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                    firebaseSignIn()
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please Enter Valid E-mail Id", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun firebaseSignIn() {
        fAuth.signInWithEmailAndPassword(
            username.text.toString(),
            password.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this.activity, PlayerCounterPageActivity::class.java)
                startActivity(intent)
                /* val action=LoginPageDirections.actionLoginPageToPlayerCounterPageFragment()
                 NavHostFragment.findNavController(this).navigate(action)*/
            } else {
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}