package com.example.servenep.UI.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.repository.UserRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {
    private lateinit var tvFullname : TextView
    private lateinit var tvCateg : TextView
    private lateinit var tvRatePer : TextView
    private lateinit var tvPhone : TextView
    private lateinit var tvAddress : TextView
    private lateinit var tvEmail : TextView
    private lateinit var tvUserType : TextView
    private lateinit var linearCat : LinearLayout
    private lateinit var linearRate : LinearLayout
    private lateinit var tvBio : TextView
    private lateinit var editprofile : Button
    private lateinit var ppimage : CircleImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        tvFullname = view.findViewById(R.id.tvFullname)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvAddress = view.findViewById(R.id.tvAddress)
        tvPhone = view.findViewById(R.id.tvPhone)
        ppimage = view.findViewById(R.id.ppimage)
        tvRatePer = view.findViewById(R.id.tvRatePer)
        tvCateg = view.findViewById(R.id.tvCateg)
        tvUserType = view.findViewById(R.id.tvUsertype)
        linearCat = view.findViewById(R.id.linearCat)
        linearRate = view.findViewById(R.id.linearRate)

        tvBio = view.findViewById(R.id.tvBio)
        editprofile = view.findViewById(R.id.editprofile)

        editprofile.setOnClickListener{
            startActivity(Intent(context, UpdateProfileActivity::class.java))
        }

        userProfile()
        return view
    }

    private fun userProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val userRepository = UserRepository()
                val response = userRepository.getUser()
                if(response.success == true){
                    val  user = response.data!!
                    val image = ServiceBuilder.loadImagePath() + user.profileImage
                    withContext(Dispatchers.Main) {
                        if (user.role == "Customer") {
                            tvFullname.setText("${user.fullName}")
                            tvEmail.setText("${user.email}")
                            tvUserType.setText("${user.role}")
                            tvAddress.setText("${user.address}")
                            tvPhone.setText("${user.phone}")
                            tvBio.setText("${user.bio}")
                            if(!user.profileImage.equals("")) {
                                Glide.with(this@ProfileFragment)
                                    .load(image)
                                    .into(ppimage)
                            }
                        }else if (user.role == "Tasker"){
                            linearCat.visibility = View.VISIBLE
                            linearRate.visibility = View.VISIBLE
                            tvFullname.setText("${user.fullName}")
                            tvCateg.setText("${user.category}")
                            tvRatePer.setText("${user.price}")
                            tvEmail.setText("${user.email}")
                            tvUserType.setText("${user.role}")
                            tvAddress.setText("${user.address}")
                            tvPhone.setText("${user.phone}")
                            tvBio.setText("${user.bio}")
                            if (!user.profileImage.equals("")) {
                                Glide.with(this@ProfileFragment)
                                    .load(image)
                                    .into(ppimage)
                            }
                        }


                    }
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        context,
                        "Error : ${ex}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}