package com.example.login_registration_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val logintext : TextView = findViewById(R.id.textView_login_now)

        logintext.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val registerButton : Button = findViewById(R.id.button_register)
        registerButton.setOnClickListener{
            performSignUp()
        }
    }

    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.editText_email_register)
        val password = findViewById<EditText>(R.id.editText_password_register)

        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all field", Toast.LENGTH_SHORT)
                .show()
            return

        }

        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent  = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext,
                        "Authentication succeed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error occured ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}