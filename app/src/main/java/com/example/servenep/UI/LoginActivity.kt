package com.example.servenep;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.servenep.R
import com.example.servenep.UI.DashboardActivity
import com.example.servenep.UI.RegisterActivity
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var forgetpassword: TextView
    private lateinit var tvSignup: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etemail=findViewById(R.id.etemail)
        etpassword=findViewById(R.id.etpassword)
        btnlogin=findViewById(R.id.btnlogin)
        forgetpassword=findViewById(R.id.forgetpassword)
        tvSignup=findViewById(R.id.tvSignup)

        btnlogin.setOnClickListener(this)
        tvSignup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnlogin->{
                loginUser()
                validation()
            }
            R.id.tvSignup->{
                val intent= Intent(
                    this,
                    RegisterActivity::class.java
                )
                startActivity(intent)
                Toast.makeText(this,"Welcome to Registration page !!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser() {
        val email = etemail.text.toString()
        val password = etpassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository =UserRepository()
                val response = repository.userLogin(email,password)
                if (response.success == true){
                    ServiceBuilder.token = "Bearer ${response.token}"
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            DashboardActivity::class.java
                        )
                    )
                    finish()
                }
                else{
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login Unsucessful!!", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            catch(ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        "$ex", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validation():Boolean {
        if(etemail.text.toString()==""){
            etemail.error="Please enter your username"
        }
        if(etpassword.text.toString()==""){
            etpassword.error="Please enter your password"
        }
        return true
    }

}