package com.example.servenep.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var forgetpassword: TextView
    private lateinit var tvSignup: TextView
    private lateinit var goToRegister: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etemail = findViewById(R.id.etemail)
        etpassword = findViewById(R.id.etpassword)
        btnlogin = findViewById(R.id.btnlogin)
        forgetpassword = findViewById(R.id.forgetpassword)
        tvSignup = findViewById(R.id.tvSignup)
        goToRegister = findViewById(R.id.goToRegister)

        btnlogin.setOnClickListener(this)
        tvSignup.setOnClickListener(this)
        goToRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnlogin -> {
                loginUser()
                validation()
            }
            R.id.goToRegister -> {
                val intent = Intent(
                    this,
                    RegisterActivity::class.java
                )
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }
            R.id.tvSignup -> {
                val intent = Intent(
                    this,
                    RegisterActivity::class.java
                )
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                Toast.makeText(this, "Welcome to Registration page !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser() {
        val email = etemail.text.toString()
        val password = etpassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.userLogin(email, password)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    ServiceBuilder.id = "${response.data!!._id}"
                    ServiceBuilder.usertype = "${response.data!!.role}"
                    saveSharedPref()
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            Home_Menu_Activity::class.java
                        )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login Unsuccessful!!", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        "$ex", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validation(): Boolean {
        if (etemail.text.toString() == "") {
            etemail.error = "Please enter your username"
        }
        if (etpassword.text.toString() == "") {
            etpassword.error = "Please enter your password"
        }
        return true
    }

    private fun saveSharedPref() {
        val email = etemail.text.toString()
        val password = etpassword.text.toString()
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }


}
