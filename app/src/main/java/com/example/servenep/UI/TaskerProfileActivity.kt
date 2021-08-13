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
    private lateinit var imgProfile:ImageView
    private lateinit var tvName:TextView
    private lateinit var tvRole:TextView
    private lateinit var etEmail:EditText
    private lateinit var etAddress:EditText
    private lateinit var etCategory:EditText
    private lateinit var etRate:EditText
    private lateinit var etPhone:EditText
    private lateinit var btnSaveChanges:Button

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_profile)

        imgProfile=findViewById(R.id.imgProfile)
        tvName=findViewById(R.id.tvName)
        tvRole=findViewById(R.id.tvRole)
        etAddress=findViewById(R.id.etAddress)
        etEmail=findViewById(R.id.etEmail)
        etCategory=findViewById(R.id.etCategory)
        etRate=findViewById(R.id.etRate)
        etPhone=findViewById(R.id.etPhone)
        btnSaveChanges=findViewById(R.id.btnSaveChanges)

        imgProfile.setOnClickListener {
            loadPopUpMenu()
        }
        loadMyProfile()

        btnSaveChanges.setOnClickListener {
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
        val popupMenu = PopupMenu(this@TaskerProfileActivity, imgProfile)
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
                        tvName.setText("${user.fullName}")
                        tvRole.setText("${user.role}")
                        etEmail.setText("${user.email}")
                        etAddress.setText("${user.address}")
                        etCategory.setText("${user.category}")
                        etRate.setText("${user.price}")
                        etPhone.setText("${user.phone}")

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
        val email = etEmail.text.toString()
        val add = etAddress.text.toString()
        val cat = etCategory.text.toString()
        val rate = etRate.text.toString()
        val phone = etPhone.text.toString()

        val userData = Users(email = email, address = add, category = cat, price = rate, phone = phone)
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
