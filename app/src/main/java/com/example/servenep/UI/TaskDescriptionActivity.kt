package com.example.servenep.UI

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.entities.Category
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
import org.json.JSONException
import org.json.JSONObject

class TaskDescriptionActivity : AppCompatActivity(), View.OnClickListener {

    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey =
        "key=" + "AAAABfaM5Us:APA91bEWU1zeRwK_7Auwv81eKkK7jLqIIqnlpeFj9HyE_TEHj3RxJSvcbbXstnhP4ST8BcKceu1uPLBMFcOOi9aPFldHIZLSSrFfvsiE97jFykLTffhIHgBCxUlBzybswbketE3K1krE"
    private val contentType = "application/json"

    private lateinit var ettile: EditText
    private lateinit var ettaskdes: EditText
    private lateinit var rbminhour: RadioButton
    private lateinit var rbmidhour: RadioButton
    private lateinit var rbmaxhour: RadioButton
    private lateinit var rbotherhour: RadioButton
    private lateinit var etprice: EditText
    private lateinit var tvLocation: TextView
    private lateinit var btnsubmit: Button
    private lateinit var btnPickLocation: Button
    private lateinit var backButtonFromAddDesc: ImageView
    private lateinit var wifiManager: WifiManager
    private var latitude: Double? = null
    private var longitude: Double? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_description)

        ettile = findViewById(R.id.ettitle)
        ettaskdes = findViewById(R.id.ettaskdes)
        rbminhour = findViewById(R.id.rbminhour)
        rbmidhour = findViewById(R.id.rbmidhour)
        rbmaxhour = findViewById(R.id.rbmaxhour)
        rbotherhour = findViewById(R.id.rbotherhour)
        tvLocation = findViewById(R.id.tvLocation)
        etprice = findViewById(R.id.etprice)
        btnsubmit = findViewById(R.id.btnsubmit)
        btnPickLocation = findViewById(R.id.btnPickLocation)
        backButtonFromAddDesc = findViewById(R.id.backButtonFromAddDesc)

        wifiManager = this.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager

        btnsubmit.setOnClickListener(this)
        backButtonFromAddDesc.setOnClickListener(this)
        btnPickLocation.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnsubmit -> {
                insertDescription()
            }
            R.id.btnPickLocation -> {
                wifiManager!!.isWifiEnabled = false
                pickLocation()
            }
            R.id.backButtonFromAddDesc -> {
                val intent = Intent(this, Home_Menu_Activity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun pickLocation() {
        val builder = PlacePicker.IntentBuilder()
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)

            //Enable Wifi
            wifiManager!!.isWifiEnabled = true
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
                PLACE_PICKER_REQUEST -> {
                    val place = PlacePicker.getPlace(this, data)
                     latitude = place.latLng.latitude
                     longitude = place.latLng.longitude
                    val placeLatLng = "Location: $latitude , $longitude"
                    tvLocation.visibility = View.VISIBLE
                    tvLocation.text = placeLatLng
                }
            }
        }
    }

    companion object {
        const val PLACE_PICKER_REQUEST = 999
    }


    private fun insertDescription() {
        val category = intent.getParcelableExtra<Users>("userDetail")
        val bookedUserId = category?._id.toString()
        val title = ettile.text.toString().trim()
        val taskDescription = ettaskdes.text.toString().trim()
        val price = etprice.text.toString().trim()
        var estimatedTime = ""
        when {
            rbminhour.isChecked -> {
                estimatedTime = "1-2 Hrs"
            }
            rbmidhour.isChecked -> {
                estimatedTime = "2-3 Hrs"
            }
            rbmaxhour.isChecked -> {
                estimatedTime = "3-More"
            }
            rbotherhour.isChecked -> {
                estimatedTime = "Not Sure"
            }
        }
        if (title == "") {
            ettile.error = "Fill up !!"
        }
        if (taskDescription == "") {
            ettaskdes.error = "Fill up !!"
        }
        if (price == "") {
            etprice.error = "Fill up !!"
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
                    val descriptionRepository = DescriptionRepository()
                    val response = descriptionRepository.descriptionInsert(description)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@TaskDescriptionActivity,
                                "Inserted Successfully!!", Toast.LENGTH_SHORT
                            ).show()
                        }
                        startActivity(
                            Intent(
                                this@TaskDescriptionActivity,
                                Home_Menu_Activity::class.java
                            )
                        )
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@TaskDescriptionActivity,
                            "error" + ex.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}