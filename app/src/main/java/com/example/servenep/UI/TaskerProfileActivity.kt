package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.servenep.R

class TaskerProfileActivity : AppCompatActivity() {
    private lateinit var Profile:EditText
    private lateinit var Name:EditText
    private lateinit var Address:EditText
    private lateinit var Role:EditText
    private lateinit var Category:EditText
    private lateinit var Rate:EditText
    private lateinit var savechange:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_profile)
        Profile=findViewById(R.id.Profile)
        Name=findViewById(R.id.Name)
        Address=findViewById(R.id.Address)
        Role=findViewById(R.id.Role)
        Category=findViewById(R.id.Category)
        Rate=findViewById(R.id.Rate)
        savechange=findViewById(R.id.savechange)
    }
}