package com.example.servenep.UI.myBookings

import android.content.Intent
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.UI.TaskDescriptionActivity
import com.example.servenep.UI.TaskerRecyclerViewActivity
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.entities.Description
import com.example.servenep.entities.Users
import com.example.servenep.repository.DescriptionRepository
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateBookingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etUpdatetitle: EditText
    private lateinit var etUpdatetaskdes: EditText
    private lateinit var rbUpdateminhour: RadioButton
    private lateinit var rbUpdatemidhour: RadioButton
    private lateinit var rbUpdatemaxhour: RadioButton
    private lateinit var rbUpdateotherhour: RadioButton
    private lateinit var etUpdateprice: EditText
    private lateinit var tvUpdateLocation: TextView
    private lateinit var btnUpdatesubmit: Button
    private lateinit var btnUpdatePickLocation: Button
    private lateinit var backButtonFromUpdateAddDesc: ImageView
    private lateinit var wifiManager: WifiManager
    private var category: String? = ""
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_booking)

        etUpdatetitle = findViewById(R.id.etUpdatetitle)
        etUpdatetaskdes = findViewById(R.id.etUpdatetaskdes)
        rbUpdateminhour = findViewById(R.id.rbUpdateminhour)
        rbUpdatemidhour = findViewById(R.id.rbUpdatemidhour)
        rbUpdatemaxhour = findViewById(R.id.rbUpdatemaxhour)
        rbUpdateotherhour = findViewById(R.id.rbUpdateotherhour)
        etUpdateprice = findViewById(R.id.etUpdateprice)
        tvUpdateLocation = findViewById(R.id.tvUpdateLocation)
        btnUpdatesubmit = findViewById(R.id.btnUpdatesubmit)
        btnUpdatePickLocation = findViewById(R.id.btnUpdatePickLocation)
        backButtonFromUpdateAddDesc = findViewById(R.id.backButtonFromUpdateAddDesc)

        val descriptionDetail = intent.getParcelableExtra<Description>("MyOffer")
        etUpdatetitle.setText("${descriptionDetail!!.title}")
        etUpdatetaskdes.setText("${descriptionDetail.taskDescription}")
        etUpdateprice.setText("${descriptionDetail.price}")

        if (descriptionDetail.estimatedTime == "1-2 Hrs"){
            rbUpdateminhour.isChecked = true
        }
        if (descriptionDetail.estimatedTime == "2-3 Hrs"){
            rbUpdatemidhour.isChecked = true
        }
        if (descriptionDetail.estimatedTime == "3-More"){
            rbUpdatemaxhour.isChecked = true
        }
        if (descriptionDetail.estimatedTime == "Not Sure"){
            rbUpdateotherhour.isChecked = true
        }
        val placeLatLng = "Location: ${descriptionDetail.latitude} , ${descriptionDetail.longitude}"
        tvUpdateLocation.text = placeLatLng

        wifiManager = this.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        btnUpdatesubmit.setOnClickListener(this)
        btnUpdatePickLocation.setOnClickListener(this)
        backButtonFromUpdateAddDesc.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUpdatesubmit -> {
                updateDescription()
            }
            R.id.btnUpdatePickLocation -> {
                wifiManager.isWifiEnabled = false
                pickLocation()
            }
            R.id.backButtonFromUpdateAddDesc -> {
                val intent = Intent(this, Home_Menu_Activity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun pickLocation() {
        val builder = PlacePicker.IntentBuilder()
        try {
            startActivityForResult(builder.build(this),
                TaskDescriptionActivity.PLACE_PICKER_REQUEST
            )

            //Enable Wifi
            wifiManager.isWifiEnabled = true
        } catch (e: GooglePlayServicesRepairableException) {
            Log.d("Exception", e.message!!)
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.d("Exception", e.message!!)
            e.printStackTrace()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                TaskDescriptionActivity.PLACE_PICKER_REQUEST -> {
                    val place = PlacePicker.getPlace(this, data)
                    latitude = place.latLng.latitude
                    longitude = place.latLng.longitude
                    val placeLatLng = "Location: $latitude , $longitude"
                    tvUpdateLocation.text = placeLatLng
                }
            }
        }
    }


    private fun updateDescription() {
        val category = intent.getParcelableExtra<Users>("userDetail")
        val bookedUserId = category?._id.toString()
        val title = etUpdatetitle.text.toString().trim()
        val taskDescription = etUpdatetaskdes.text.toString().trim()
        val price = etUpdateprice.text.toString().trim()
        var estimatedTime = ""
        when {
            rbUpdateminhour.isChecked -> {
                estimatedTime = "1-2 Hrs"
            }
            rbUpdatemidhour.isChecked -> {
                estimatedTime = "2-3 Hrs"
            }
            rbUpdatemaxhour.isChecked -> {
                estimatedTime = "3-More"
            }
            rbUpdateotherhour.isChecked -> {
                estimatedTime = "Not Sure"
            }
        }
        if (title == "") {
            etUpdatetitle.error = "Fill up !!"
        }
        if (taskDescription == "") {
            etUpdatetaskdes.error = "Fill up !!"
        }
        if (price == "") {
            etUpdateprice.error = "Fill up !!"
        } else {
            val description = Description(
                bookedUserId = bookedUserId,
                title = title,
                taskDescription = taskDescription,
                price = price,
                estimatedTime = estimatedTime,
                latitude = latitude,
                longitude = longitude,
                addedby = ServiceBuilder.id.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                // for API
                try {
                    val descriptionDetail = intent.getParcelableExtra<Description>("MyOffer")
                    val descriptionRepository = DescriptionRepository()
                    val response = descriptionRepository.updateDescription("${descriptionDetail!!._id}",description)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@UpdateBookingActivity,
                                "Updated Successfully!!", Toast.LENGTH_SHORT
                            ).show()
                        }
                        startActivity(
                            Intent(
                                this@UpdateBookingActivity,
                                Home_Menu_Activity::class.java
                            )
                        )
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@UpdateBookingActivity,
                            "error" + ex.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}