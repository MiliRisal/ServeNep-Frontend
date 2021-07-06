package com.example.servenep.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.servenep.R
import com.example.servenep.entities.Users
import com.example.servenep.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etfullname: EditText
    private lateinit var etemail: EditText
    private lateinit var etaddress: EditText
    private lateinit var etphone: EditText
    private lateinit var etpassword: EditText
    private lateinit var rduser: RadioButton
    private lateinit var rdtasker: RadioButton
    private lateinit var btnsignup: Button
    private lateinit var haveaccount: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etfullname=findViewById(R.id.etfullname)
        etemail=findViewById(R.id.etemail)
        etaddress=findViewById(R.id.etaddress)
        etphone=findViewById(R.id.etphone)
        rduser=findViewById(R.id.rduser)
        rdtasker=findViewById(R.id.rdtasker)
        etpassword=findViewById(R.id.etpassword)
        btnsignup=findViewById(R.id.btnsignup)
        haveaccount=findViewById(R.id.haveaccount)

        btnsignup.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnsignup->{
                insertUser()
            }
        }
    }

    private fun insertUser() {
        val fullName = etfullname.text.toString().trim()
        val email = etemail.text.toString().trim()
        val phone = etphone.text.toString().trim()
        val address = etaddress.text.toString().trim()
        val password = etpassword.text.toString().trim()
        //val confirmPassword = etcpassword.text.toString().trim()
        var role = ""
        when {
            rduser.isChecked -> {
                role = "Customer"
            }
            rdtasker.isChecked -> {
                role = "Tasker"
            }
        }

//        if (password != confirmPassword) {
//            etpassword.error = "Password does not match!!"
//            etpassword.requestFocus()
//            return
//        } else {
            val users = Users(
                fullName = fullName,
                email = email,
                phone = phone,
                password = password,
                address = address,
                role = role
            )
            CoroutineScope(Dispatchers.IO).launch {
                // for API
                try {
                    val userRepository = UserRepository()
                    val response = userRepository.userRegister(users)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Register Successfully!!", Toast.LENGTH_SHORT
                            ).show()

                        }
                        startActivity(
                            Intent(
                                this@RegisterActivity,
                                LoginActivity::class.java
                            )
                        )
                        finish()
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        /*val snack =
                            Snackbar.make(
                                linearLayout,
                                "Username and Email cannot be Duplicate!!",
                                Snackbar.LENGTH_SHORT
                            )
                        snack.show()*/
                    }
                }
            }
        //}
    }


}