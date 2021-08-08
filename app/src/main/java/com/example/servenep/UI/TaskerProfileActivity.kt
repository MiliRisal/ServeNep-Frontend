package com.example.servenep.UI

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import com.example.servenep.R
import java.text.SimpleDateFormat
import java.util.*

class TaskerProfileActivity : AppCompatActivity() {
    private lateinit var Profile:EditText
    private lateinit var Name:EditText
    private lateinit var Email:EditText
    private lateinit var Address:EditText
    private lateinit var Category:EditText
    private lateinit var Rate:EditText
    private lateinit var savechange:Button
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_profile)
        Profile=findViewById(R.id.Profile)
        Name=findViewById(R.id.Name)
        Address=findViewById(R.id.Address)
        Email=findViewById(R.id.Email)
        Category=findViewById(R.id.Category)
        Rate=findViewById(R.id.Rate)
        savechange=findViewById(R.id.savechange)

        Profile.setOnClickListener {
            loadPopUpMenu()
        }

        requestPermissions(permissions)
    }
    private fun requestPermissions(permissions : Array<String>) {
        ActivityCompat.requestPermissions(
            this,
            permissions, 1
        )
    }
    private fun loadPopUpMenu() {
        // Load pop up menu
        val popupMenu = PopupMenu(this@TaskerProfileActivity, Profile)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }
    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }






    }
