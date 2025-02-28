package com.firstproject.firebasics

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth // Firebase authentication instance


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edEmail = findViewById(R.id.edEmail)
        edPassword = findViewById(R.id.edPassword)
        btnLogin =  findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        auth = FirebaseAuth.getInstance()

        // Navigate to RegisterActivity
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Login user
        btnLogin.setOnClickListener {
            val email = edEmail.text.toString().trim()
            val password = edPassword.text.toString().trim()

            // Validate input
            if (email.isEmpty()) {
                edEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                edPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            // Firebase Authentication - Sign in user
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                        // Redirect to main screen (Replace `MainActivity` with your home screen)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish() // Close LoginActivity
                    } else {
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }


    }
}