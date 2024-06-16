package com.example.easyfood.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easyfood.R
import com.example.easyfood.ui.activites.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var signIn: Button
    private lateinit var signUp: TextView

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        // Ánh xạ các view từ layout
        editTextEmail = findViewById(R.id.email) as TextInputEditText
        editTextPassword = findViewById(R.id.password) as TextInputEditText
        signIn = findViewById(R.id.sign_in) as Button
        signUp = findViewById(R.id.sign_up) as TextView

        // Xử lý sự kiện click cho nút signUp
        signUp.setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
            finish()
        }

        // Xử lý sự kiện click cho nút signIn
        signIn.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Kiểm tra nếu email hoặc password trống
            if (TextUtils.isEmpty(email)) {
                editTextEmail.error = "Enter Email"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                editTextPassword.error = "Enter Password"
                return@setOnClickListener
            }

            // Xác thực người dùng
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@Login, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}