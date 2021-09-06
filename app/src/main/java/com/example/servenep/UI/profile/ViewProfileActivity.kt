package com.example.servenep.UI.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.entities.Users
import com.example.servenep.repository.UserRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewProfileActivity : AppCompatActivity() {
    private lateinit var tvViewFullName : TextView
    private lateinit var tvViewCateg : TextView
    private lateinit var tvViewRatePer : TextView
    private lateinit var tvViewPhone : TextView
    private lateinit var tvViewAddress : TextView
    private lateinit var tvViewEmail : TextView
    private lateinit var tvViewUsertype : TextView
    private lateinit var linearViewCat : LinearLayout
    private lateinit var linearViewRate : LinearLayout
    private lateinit var tvViewBio : TextView
    private lateinit var backUserViewButton : ImageView
    private lateinit var ppViewImage : CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)
        tvViewFullName = findViewById(R.id.tvViewFullName)
        tvViewCateg = findViewById(R.id.tvViewCateg)
        tvViewRatePer = findViewById(R.id.tvViewRatePer)
        tvViewPhone = findViewById(R.id.tvViewPhone)
        tvViewAddress = findViewById(R.id.tvViewAddress)
        tvViewEmail = findViewById(R.id.tvViewEmail)
        tvViewUsertype = findViewById(R.id.tvViewUsertype)
        linearViewCat = findViewById(R.id.linearViewCat)
        linearViewRate = findViewById(R.id.linearViewRate)
        tvViewBio = findViewById(R.id.tvViewBio)
        backUserViewButton = findViewById(R.id.backUserViewButton)
        ppViewImage = findViewById(R.id.ppViewImage)

        backUserViewButton.setOnClickListener {
            startActivity(Intent(this, Home_Menu_Activity::class.java))
        }

        viewUserProfile()

    }

    private fun viewUserProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val users = intent.getParcelableExtra<Users>("userDetail")
                val userRepository = UserRepository()
                val response = userRepository.getUserById("${users!!._id}")
                if(response.success == true){
                    val  user = response.data!!
                    val image = ServiceBuilder.loadImagePath() + user.profileImage
                    withContext(Dispatchers.Main) {
                        if (user.role == "Customer") {
                            tvViewFullName.setText("${user.fullName}")
                            tvViewEmail.setText("${user.email}")
                            tvViewUsertype.setText("${user.role}")
                            tvViewAddress.setText("${user.address}")
                            tvViewPhone.setText("${user.phone}")
                            tvViewBio.setText("${user.bio}")
                            if(!user.profileImage.equals("")) {
                                Glide.with(this@ViewProfileActivity)
                                    .load(image)
                                    .into(ppViewImage)
                            }
                        }else if (user.role == "Tasker"){
                            linearViewCat.visibility = View.VISIBLE
                            linearViewRate.visibility = View.VISIBLE
                            tvViewFullName.setText("${user.fullName}")
                            tvViewEmail.setText("${user.email}")
                            tvViewUsertype.setText("${user.role}")
                            tvViewAddress.setText("${user.address}")
                            tvViewPhone.setText("${user.phone}")
                            tvViewBio.setText("${user.bio}")
                            tvViewCateg.setText("${user.category}")
                            tvViewRatePer.setText("${user.price}")
                            if(!user.profileImage.equals("")) {
                                Glide.with(this@ViewProfileActivity)
                                    .load(image)
                                    .into(ppViewImage)
                            }
                        }


                    }
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        this@ViewProfileActivity,
                        "Error : ${ex}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}