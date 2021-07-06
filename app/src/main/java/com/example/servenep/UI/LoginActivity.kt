package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.servenep.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var forgetpassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etemail=findViewById(R.id.etemail)
        etpassword=findViewById(R.id.etpassword)
        btnlogin=findViewById(R.id.btnlogin)
        forgetpassword=findViewById(R.id.forgetpassword)

        btnlogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnlogin->{
                loginUser()
            }
        }
    }

    private fun loginUser() {
        val etemail = etemail.text.toString()
        val etpassword = etpassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {

        }
    }
}