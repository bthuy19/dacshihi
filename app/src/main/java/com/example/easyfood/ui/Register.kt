package com.example.easyfood.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easyfood.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

    class Register : AppCompatActivity() {
        private lateinit var editTextEmail: TextInputEditText
        private lateinit var editTextPassword: TextInputEditText
        private lateinit var signUp: Button
        private lateinit var signIn: TextView

        private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.register)

            // Ánh xạ các view từ layout
            editTextEmail = findViewById(R.id.email)
            editTextPassword = findViewById(R.id.password)
            signIn = findViewById(R.id.sign_in)
            signUp = findViewById(R.id.sign_up)

            // Xử lý sự kiện click cho nút signIn
            signIn.setOnClickListener {
                val intent = Intent(this@Register, Login::class.java)
                startActivity(intent)
                finish()
            }

            // Xử lý sự kiện click cho nút signUp
            signUp.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()

                // Kiểm tra đầu vào rỗng
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.error = "Enter Email"
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.error = "Enter Password"
                    return@setOnClickListener
                }

                // Kiểm tra độ dài của password
                if (password.length < 6) {
                    editTextPassword.error = "Password must be at least 6 characters"
                    return@setOnClickListener
                }

                // Xác thực người dùng
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@Register, "Registration Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@Register, Login::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Register, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
