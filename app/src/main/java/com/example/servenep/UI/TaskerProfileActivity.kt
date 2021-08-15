package com.example.servenep.UI

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.entities.Users
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TaskerProfileActivity : AppCompatActivity() {
    private lateinit var Profile:ImageView
    private lateinit var Name:EditText
    private lateinit var Email:EditText
    private lateinit var Address:EditText
    private lateinit var Category:EditText
    private lateinit var Rate:EditText
    private lateinit var tvRole:TextView
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

        Profile=findViewById(R.id.imgProfile)
        Name=findViewById(R.id.tvName)
        tvRole=findViewById(R.id.tvRole)
        Address=findViewById(R.id.etAddress)
        Email=findViewById(R.id.etEmail)
        Category=findViewById(R.id.etCategory)
        Rate=findViewById(R.id.etRate)
        savechange=findViewById(R.id.etPhone)

        Profile.setOnClickListener {
            loadPopUpMenu()
        }
        loadMyProfile()

        savechange.setOnClickListener {
            SaveChanges()
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

    private fun loadMyProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.getUser()
                if(response.success == true){
                    val user = response.data!!
                    withContext(Dispatchers.Main){
                        val user = response.data!!
                        Name.setText("${user.fullName}")
                        Address.setText("${user.address}")
                        tvRole.setText("${user.role}")
                        Category.setText("${user.category}")
                        Rate.setText("${user.price}")
                    }
                }
            }
            catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@TaskerProfileActivity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun SaveChanges() {
        val fn = Name.text.toString()
        val add = Address.text.toString()
        val cat = Category.text.toString()
        val rate = Rate.text.toString()

        val userData = Users(fullName = fn, address = add, category = cat, price = rate)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository =UserRepository()
                val response = userRepository.updateUser(ServiceBuilder.Id!!, userData)
                if (response.success==true){

                    withContext(Dispatchers.Main){
                        Toast.makeText(this@TaskerProfileActivity,
                        "Updated Successully",
                        Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            catch(ex : java.lang.Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@TaskerProfileActivity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }





}
