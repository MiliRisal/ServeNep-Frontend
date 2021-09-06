package com.example.servenep.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.UI.location.ViewLocationActivity
import com.example.servenep.entities.AcceptedTask
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ViewTasksActivity : AppCompatActivity() {
    private lateinit var tvViewAcceptedName: TextView
    private lateinit var tvViewAcceptedTitle: TextView
    private lateinit var tvViewAcceptedTaskDesc: TextView
    private lateinit var tvViewAcceptedEstTime: TextView
    private lateinit var tvViewAcceptedEstPrice: TextView
    private lateinit var btnViewAcceptedLocation: Button
    private lateinit var backButtonFromViewAcceptedDesc: ImageView
    private var name : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tasks)
        getDescription()
        tvViewAcceptedName = findViewById(R.id.tvViewAcceptedName)
        tvViewAcceptedTitle = findViewById(R.id.tvViewAcceptedTitle)
        tvViewAcceptedTaskDesc = findViewById(R.id.tvViewAcceptedTaskDesc)
        tvViewAcceptedEstTime = findViewById(R.id.tvViewAcceptedEstTime)
        tvViewAcceptedEstPrice = findViewById(R.id.tvViewAcceptedEstPrice)
        btnViewAcceptedLocation = findViewById(R.id.btnViewAcceptedLocation)
        backButtonFromViewAcceptedDesc = findViewById(R.id.backButtonFromViewAcceptedDesc)

        backButtonFromViewAcceptedDesc.setOnClickListener {
            startActivity(Intent(this,Home_Menu_Activity::class.java))
            finish()
        }
        btnViewAcceptedLocation.setOnClickListener {
            val location = intent.getParcelableExtra<AcceptedTask>("Task")
            if(location!!.latitude != null && location.longitude != null){
                val intent = Intent(this, ViewLocationActivity::class.java)
                intent.putExtra("location", location)
                intent.putExtra("name", name)
                intent.putExtra("Accepted", "Accepted")
                startActivity(intent)
            }
            else{
                Toast.makeText(
                    this,
                    "You haven't select the location!!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getDescription() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val offer = intent.getParcelableExtra<AcceptedTask>("Task")
                val userRepository = UserRepository()
                val response = userRepository.getUserById("${offer!!.userid}")
                withContext(Dispatchers.Main) {
                    if (response.success == true) {
                        tvViewAcceptedName.text = "${response.data!!.fullName}"
                        name = "${response.data.fullName}"
                        tvViewAcceptedTitle.text = "${offer.descTitle}"
                        tvViewAcceptedTaskDesc.text = "${offer.description}"
                        tvViewAcceptedEstTime.text = "${offer.time}"
                        tvViewAcceptedEstPrice.text = "Rs.${offer.rate} per hour."
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ViewTasksActivity,
                        "Error : $ex", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
