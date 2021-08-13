package com.example.servenep.UI

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*


import androidx.appcompat.app.AppCompatActivity
import com.example.servenep.R
import com.example.servenep.entities.Users
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val category = arrayOf("--Select--","Cleaner","Electrician","Delivery","Carpenter","Plumber","Mechanic")
    private lateinit var selectedItem : String
    private lateinit var etfullname: EditText
    private lateinit var etemail: EditText
    private lateinit var etaddress: EditText
    private lateinit var etphone: EditText
    private lateinit var etpassword: EditText
    private lateinit var tvCat: TextView
    private lateinit var tvPrice: TextView
    private lateinit var spCat: Spinner
    private lateinit var etPrice: EditText
    private lateinit var rduser: RadioButton
    private lateinit var rdtasker: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnsignup: Button
    private lateinit var tvSignin: TextView

    val MIN_PASSWORD_LENGTH = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etfullname=findViewById(R.id.etfullname)
        etemail=findViewById(R.id.etemail)
        etaddress=findViewById(R.id.etaddress)
        etphone=findViewById(R.id.etphone)
        spCat=findViewById(R.id.etCat)
        etPrice=findViewById(R.id.etPrice)
<<<<<<< HEAD
=======
        tvCat=findViewById(R.id.tvcat)
>>>>>>> ccb23ec45852e35f146daa27d96daeb06baab1ff
        tvPrice=findViewById(R.id.tvPrice)
        tvCat=findViewById(R.id.tvCat)
        rduser=findViewById(R.id.rduser)
        rdtasker=findViewById(R.id.rdtasker)
        radioGroup=findViewById(R.id.radioGroup)
        etpassword=findViewById(R.id.etpassword)
        btnsignup=findViewById(R.id.btnsignup)
        tvSignin=findViewById(R.id.tvSignin)

        btnsignup.setOnClickListener(this)
        tvSignin.setOnClickListener(this)


        //radio group vis/invis
        radioGroup.setOnCheckedChangeListener(object :RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int){
                when(checkedId){
                    R.id.rduser->{
                        tvCat.visibility = View.GONE
                        spCat.visibility = View.GONE
                        tvPrice.visibility = View.GONE
                        etPrice.visibility = View.GONE
                    }
                    R.id.rdtasker->{
                        tvCat.visibility = View.VISIBLE
                        spCat.visibility = View.VISIBLE
                        tvPrice.visibility = View.VISIBLE
                        etPrice.visibility = View.VISIBLE

                        //array value adapter for spinner
                        val adapter = ArrayAdapter(
                            this@RegisterActivity,
                            android.R.layout.simple_list_item_1,
                            category
                        )
                        spCat.adapter=adapter

                        spCat.onItemSelectedListener=
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
        })
    }
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnsignup->{
                insertUser()
            }
            R.id.tvSignin->{
                val intent= Intent(
                    this,
                    LoginActivity::class.java
                )
                startActivity(intent)
                Toast.makeText(this,"Welcome to login page !!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertUser() {
        val fullName = etfullname.text.toString().trim()
        val email = etemail.text.toString().trim()
        val phone = etphone.text.toString().trim()
        val address = etaddress.text.toString().trim()
        val password = etpassword.text.toString().trim()
        val price = etPrice.text.toString()
        val category = selectedItem

        var role = ""
        when {
            rduser.isChecked -> {
                role = "Customer"
            }
            rdtasker.isChecked -> {
                role = "Tasker"
            }
        }

        if (fullName == "") {
            etfullname.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.error = "Please enter valid email address !!"
            Toast.makeText(this, "Please enter valid email address !!", Toast.LENGTH_SHORT).show()
        }
        if (phone == "") {
            etphone.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }
        if (address == "") {
            etaddress.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            etpassword.error = "Too weak! At least 6 characters required!"
        } else {
            //APi starts
            val users = Users(
                fullName = fullName,
                email = email,
                phone = phone,
                password = password,
                address = address,
                role = role,
                category= category,
                price = price


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


                            startActivity(
                                Intent(
                                    this@RegisterActivity,
                                    LoginActivity::class.java
                                )
                            )
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@RegisterActivity, "error" + ex.toString(), Toast.LENGTH_SHORT
                        ).show()
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
        }
    }
}