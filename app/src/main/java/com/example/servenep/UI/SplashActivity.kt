package com.example.servenep.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.repository.UserRepository
import com.example.servenep.Home_Menu_Activity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private lateinit var lottie: LottieAnimationView
    private lateinit var linearLayout: LinearLayout
    private var email : String =""
    private var password : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        linearLayout=findViewById(R.id.linearlayout)
        lottie=findViewById(R.id.lottie)
        lottie.animate().translationX(-2000F).setDuration(1000).startDelay = 3000

        getSharedPref()

        if (email==""){
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                startActivity(
                    Intent(
                        this@SplashActivity, LoginActivity::class.java
                    )
                )
            }
        }
        else{
            validate()
        }
    }

    private fun getSharedPref() {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        email = sharedPref.getString("email", "").toString()
        password = sharedPref.getString("password", "").toString()
    }

    private fun validate() {
        val email = email
        val password = password
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            try {
                val repository = UserRepository()
                val response = repository.userLogin(email, password)
                if (response.success == true ) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    ServiceBuilder.Id = response.data!!._id
                    ServiceBuilder.usertype = response.data.role

                    startActivity(
                        Intent(
                            this@SplashActivity,
                            Home_Menu_Activity::class.java
                        )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                            Snackbar.make(
                                linearLayout,
                                "Invalid credentials",
                                Snackbar.LENGTH_LONG
                            )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SplashActivity,
                        "$ex", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}