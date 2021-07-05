package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.example.servenep.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var etfullname: EditText
    private lateinit var etemail: EditText
    private lateinit var etaddress: EditText
    private lateinit var etphone: EditText
    private lateinit var etpassword: EditText
    private lateinit var rduser: RadioButton
    private lateinit var rdtasker: RadioButton
    private lateinit var btnsignup: Button
    private lateinit var haveaccount: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etfullname=findViewById(R.id.etfullname)
        etemail=findViewById(R.id.etemail)
        etaddress=findViewById(R.id.etaddress)
        etphone=findViewById(R.id.etphone)
        rduser=findViewById(R.id.rduser)
        rdtasker=findViewById(R.id.rdtasker)
        etpassword=findViewById(R.id.etpassword)
        btnsignup=findViewById(R.id.btnsignup)
        haveaccount=findViewById(R.id.haveaccount)
    }
}