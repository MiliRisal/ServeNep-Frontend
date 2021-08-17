package com.example.servenep.UI.profile

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.UI.LoginActivity
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.entities.Users
import com.example.servenep.repository.UserRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UpdateProfileActivity : AppCompatActivity() {
    private val category = arrayOf("--Select--","Cleaner","Electrician","Delivery","Carpenter","Plumber","Mechanic")
    private var selectedItem : String? = ""
    private var userType : String? = ""
    private lateinit var etUpFullname : EditText
    private lateinit var spCateg : Spinner
    private lateinit var etUpRatePer : EditText
    private lateinit var etUpPhone : EditText
    private lateinit var etUpAddress : EditText
    private lateinit var etUpEmail : EditText
    private lateinit var etUpBio : EditText
    private lateinit var linearUpCat : LinearLayout
    private lateinit var linearUpRate : LinearLayout
    private lateinit var updateProfile : Button
    private lateinit var btnChangeImage : ImageView
    private lateinit var backButton : ImageView
    private lateinit var Uppimage : CircleImageView

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        btnChangeImage = findViewById(R.id.btnChangeImage)
        backButton = findViewById(R.id.backButton)
        Uppimage = findViewById(R.id.Uppimage)
        etUpFullname = findViewById(R.id.etUpFullname)
        spCateg = findViewById(R.id.spCateg)
        etUpRatePer = findViewById(R.id.etUpRatePer)
        etUpPhone = findViewById(R.id.etUpPhone)
        etUpAddress = findViewById(R.id.etUpAddress)
        etUpEmail = findViewById(R.id.etUpEmail)
        etUpBio = findViewById(R.id.etUpBio)
        linearUpCat = findViewById(R.id.linearUpCat)
        linearUpRate = findViewById(R.id.linearUpRate)
        updateProfile = findViewById(R.id.updateProfile)

        getUser()
        val adapter = ArrayAdapter(
            this@UpdateProfileActivity,
            android.R.layout.simple_list_item_1,
            category
        )
        spCateg.adapter=adapter

        spCateg.onItemSelectedListener=
            object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedItem = parent?.getItemAtPosition(position).toString()
                    //Toast.makeText(this@RegisterActivity, "$selectedItem", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        backButton.setOnClickListener {
            startActivity(Intent(this, Home_Menu_Activity::class.java))
        }
        btnChangeImage.setOnClickListener{
            popUpMenu()
        }
        updateProfile.setOnClickListener{
            updateUser()
        }

        requestPermissions(permissions)
    }
    private fun requestPermissions(permissions : Array<String>) {
        ActivityCompat.requestPermissions(
            this,
            permissions, 1
        )
    }

    private fun getUser() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val userRepository = UserRepository()
                val response = userRepository.getUser()
                if(response.success == true){
                    val  user = response.data!!
                    userType = "${user.role}"
                    val image = ServiceBuilder.loadImagePath() + user.profileImage
                    withContext(Dispatchers.Main) {
                        if (user.role == "Customer") {
                            etUpFullname.setText("${user.fullName}")
                            etUpEmail.setText("${user.email}")
                            etUpAddress.setText("${user.address}")
                            etUpPhone.setText("${user.phone}")
                            etUpBio.setText("${user.bio}")
                            if (!user.profileImage.equals("")) {
                                Glide.with(this@UpdateProfileActivity)
                                    .load(image)
                                    .into(Uppimage)
                            }
                        }else if (user.role == "Tasker"){
                            linearUpCat.visibility = View.VISIBLE
                            linearUpRate.visibility = View.VISIBLE
                            etUpFullname.setText("${user.fullName}")
                            etUpRatePer.setText("${user.price}")
                            etUpEmail.setText("${user.email}")
                            etUpAddress.setText("${user.address}")
                            etUpPhone.setText("${user.phone}")
                            etUpBio.setText("${user.bio}")
                            selectedItem = "${user.category}"
                            if (!user.profileImage.equals("")) {
                                Glide.with(this@UpdateProfileActivity)
                                    .load(image)
                                    .into(Uppimage)
                            }
                            val adapter = ArrayAdapter(
                                this@UpdateProfileActivity,
                                android.R.layout.simple_list_item_1,
                                category
                            )
                            spCateg.adapter=adapter

                            spCateg.onItemSelectedListener=
                                object : AdapterView.OnItemSelectedListener{
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        selectedItem = parent?.getItemAtPosition(position).toString()
                                        //Toast.makeText(this@RegisterActivity, "$selectedItem", Toast.LENGTH_SHORT).show()
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                        TODO("Not yet implemented")
                                    }
                                }
                        }
                    }
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        this@UpdateProfileActivity,
                        "Error : ${ex}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateUser() {
        val fullName = etUpFullname.text.toString().trim()
        val price = etUpRatePer.text.toString().trim()
        val email = etUpEmail.text.toString().trim()
        val address = etUpAddress.text.toString().trim()
        val phone = etUpPhone.text.toString().trim()
        val bio = etUpBio.text.toString().trim()
        val category = selectedItem

        if(userType == "Customer"){
            linearUpCat.visibility = View.GONE
            linearUpRate.visibility = View.GONE
            val users = Users(
                fullName = fullName,
                email = email,
                phone = phone,
                address = address,
                bio = bio
            )
            CoroutineScope(Dispatchers.IO).launch {
                // for API
                try {
                    val userRepository = UserRepository()
                    val response = userRepository.updateUser(ServiceBuilder.id!!, users)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            if (profileUrl != null){
                                uploadProfile(ServiceBuilder.id!!)
                            }
                            Toast.makeText(
                                this@UpdateProfileActivity,
                                "User Updated Successfully", Toast.LENGTH_SHORT
                            ).show()
                            startActivity(
                                Intent(
                                    this@UpdateProfileActivity,
                                    Home_Menu_Activity::class.java
                                )
                            )
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@UpdateProfileActivity, "error " + ex.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        else{
            val users = Users(
                fullName = fullName,
                email = email,
                phone = phone,
                address = address,
                bio = bio,
                category = category,
                price = price
            )
            CoroutineScope(Dispatchers.IO).launch {
                // for API
                try {
                    val userRepository = UserRepository()
                    val response = userRepository.updateUser(ServiceBuilder.id!!, users)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            if (profileUrl != null){
                                uploadProfile(ServiceBuilder.id!!)
                            }
                            Toast.makeText(
                                this@UpdateProfileActivity,
                                "User Updated Successfully", Toast.LENGTH_SHORT
                            ).show()
                            startActivity(
                                Intent(
                                    this@UpdateProfileActivity,
                                    Home_Menu_Activity::class.java
                                )
                            )
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@UpdateProfileActivity, "error " + ex.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }



    }

    private fun popUpMenu() {
        val popupMenu = PopupMenu(this, Uppimage)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId){
                R.id.menuGallery ->
                    openStorage()
            }
            true
        }
        popupMenu.show()
    }
    private var REQUEST_STORAGE_CODE = 0
    private var profileUrl: String? = null

    private fun openStorage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_STORAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_STORAGE_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                profileUrl = cursor.getString(columnIndex)
                Uppimage.setImageBitmap(BitmapFactory.decodeFile(profileUrl))
                cursor.close()
            }
        }
    }
    private fun uploadProfile(userID: String){
        if (profileUrl != null){
            val file = File(profileUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("image/"+file.extension.lowercase().replace("jpg","jpeg")),file)

            val body = MultipartBody.Part.createFormData("profileImage", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepository = UserRepository()
                    val response = userRepository.uploadProfile(userID, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@UpdateProfileActivity,
                                "Image Uploaded",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }   catch (ex : Exception){
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            ex.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}