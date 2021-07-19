package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.servenep.R

class TaskerJobDescriptionActivity : AppCompatActivity() {
    private lateinit var etTaskerCategory:EditText
    private lateinit var etRate:EditText
    private lateinit var etArea:EditText
    private lateinit var btnTaskerSubmit:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_job_description)

        etTaskerCategory=findViewById(R.id.etTaskerCategory)
        etRate=findViewById(R.id.etRate)
        etArea=findViewById(R.id.etArea)
        btnTaskerSubmit=findViewById(R.id.btnTaskerSubmit)
    }
}