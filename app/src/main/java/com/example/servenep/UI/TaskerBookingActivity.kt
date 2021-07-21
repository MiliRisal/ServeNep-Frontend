package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.servenep.R

class TaskerBookingActivity : AppCompatActivity() {
    private lateinit var imgProfile:ImageView
    private lateinit var tvName:TextView
    private lateinit var tvCategory:TextView
    private lateinit var tvDesc:TextView
    private lateinit var tvestRate:TextView
    private lateinit var IconBook:ImageView
    private lateinit var tvBook:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_booking)

        imgProfile=findViewById(R.id.imgProfile)
        tvName=findViewById(R.id.tvName)
        tvCategory=findViewById(R.id.tvCategory)
        tvDesc=findViewById(R.id.tvDesc)
        tvestRate=findViewById(R.id.tvestRate)
        IconBook=findViewById(R.id.IconBook)
        tvBook=findViewById(R.id.tvBook)
    }
}