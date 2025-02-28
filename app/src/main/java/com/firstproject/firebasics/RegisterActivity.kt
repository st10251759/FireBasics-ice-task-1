package com.firstproject.firebasics

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var edPasswordConfirms: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    //declare firebase auth as variable
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edEmail = findViewById(R.id.edEmail)
        edPassword = findViewById(R.id.edPassword)
        edPasswordConfirms = findViewById(R.id.edPasswordConfirmed)
        btnLogin =  findViewById(R.id.btnLoginPage)
        btnRegister = findViewById(R.id.btnRegisterPage)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            // Create an Intent to navigate to CalculatorScreen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Start the CalculatorScreen activity
        }

        btnRegister.setOnClickListener {
            val email = edEmail.text.toString().trim()
            val password = edPassword.text.toString().trim()
            val confirmPassword = edPasswordConfirms.text.toString().trim()

            // Input validation
            if (email.isEmpty()) {
                edEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                edPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }
            if (confirmPassword.isEmpty()) {
                edPasswordConfirms.error = "Please confirm your password"
                return@setOnClickListener
            }

            // Firebase Authentication - Register user
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                        // Redirect to LoginActivity
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish() // Close RegisterActivity
                    } else {
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }

        }

    }


}