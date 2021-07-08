package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.servenep.R

class DashboardActivity : AppCompatActivity() {
    private lateinit var homeicon:ImageView
    private lateinit var profileicon:ImageView
    private lateinit var toolsicon: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        homeicon=findViewById(R.id.homeicon)
        profileicon=findViewById(R.id.profileicon)
        toolsicon=findViewById(R.id.toolsicon)


    }
}