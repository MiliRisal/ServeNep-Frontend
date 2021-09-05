package com.example.servenep.UI

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.databinding.ActivityJobNotificationBinding
import com.example.servenep.entities.AcceptedTask
import com.example.servenep.entities.Description
import com.example.servenep.entities.Users
import com.example.servenep.repository.AcceptedTaskRepository
import com.example.servenep.repository.DescriptionRepository
import com.example.servenep.repository.UserRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class JobNotificationActivity : AppCompatActivity() {
    //
//    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
//    private val serverKey =
//        "key=" + "AAAABfaM5Us:APA91bEWU1zeRwK_7Auwv81eKkK7jLqIIqnlpeFj9HyE_TEHj3RxJSvcbbXstnhP4ST8BcKceu1uPLBMFcOOi9aPFldHIZLSSrFfvsiE97jFykLTffhIHgBCxUlBzybswbketE3K1krE"
//    private val contentType = "application/json"
    private lateinit var tvViewName: TextView
    private lateinit var tvViewTitle: TextView
    private lateinit var tvViewTaskDesc: TextView
    private lateinit var tvViewEstTime: TextView
    private lateinit var tvViewEstPrice: TextView
    private lateinit var btnViewLocation: Button
    private lateinit var btnViewAccept: Button
    private lateinit var btnViewReject: Button
    private lateinit var backButtonFromViewDesc: ImageView
    private var name: String = ""

//    private val requestQueue: RequestQueue by lazy {
//        Volley.newRequestQueue(this.applicationContext)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_notification)
        val binding = DataBindingUtil.setContentView<ActivityJobNotificationBinding>(
            this, R.layout.activity_job_notification
        )

        //FirebaseMessaging.getInstance().subscribeToTopic("/topics/Enter_topic")
        tvViewName = findViewById(R.id.tvViewName)
        tvViewTitle = findViewById(R.id.tvViewTitle)
        tvViewTaskDesc = findViewById(R.id.tvViewTaskDesc)
        tvViewEstTime = findViewById(R.id.tvViewEstTime)
        tvViewEstPrice = findViewById(R.id.tvViewEstPrice)
        btnViewLocation = findViewById(R.id.btnViewLocation)
        btnViewAccept = findViewById(R.id.btnViewAccept)
        btnViewReject = findViewById(R.id.btnViewReject)
        backButtonFromViewDesc = findViewById(R.id.backButtonFromViewDesc)
        val booking = intent.getStringExtra("Booking")
        if( booking == "Booking"){
            getDescription()
            btnViewAccept.visibility = View.GONE
            btnViewReject.visibility = View.GONE
        }
        getDescription()
        btnViewLocation.setOnClickListener {
            val location = intent.getParcelableExtra<Description>("MyOffer")
            if (location!!.latitude != null && location.longitude != null) {
                val intent = Intent(this, ViewLocationActivity::class.java)
                intent.putExtra("location", location)
                intent.putExtra("name", name)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "You haven't select the location!!", Toast.LENGTH_SHORT
                ).show()
            }
        }
        backButtonFromViewDesc.setOnClickListener {
            startActivity(Intent(this, Home_Menu_Activity::class.java))
            finish()
        }

        btnViewAccept.setOnClickListener {
            val offer = intent.getParcelableExtra<Description>("MyOffer")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    withContext(Dispatchers.Main) {
                        val desc = AcceptedTask(
                            descTitle = offer!!.title,
                            description = offer.taskDescription,
                            rate = offer.price,
                            time = offer.estimatedTime,
                            acceptedby = offer.bookedUserId,
                            userid = offer.addedby.toString()
                        )
                        val acceptedTaskRepository = AcceptedTaskRepository()
                        val description = acceptedTaskRepository.insertAcceptedTask(desc)
                        if (description.success == true) {
                            val descriptionRepository = DescriptionRepository()
                            val response = descriptionRepository.deleteDescription(offer._id!!)
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@JobNotificationActivity,
                                        "Accepted", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@JobNotificationActivity,
                            ex.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
//            if (!TextUtils.isEmpty(binding.tvViewName.text)) {
//                val topic = "/topics/Enter_topic"
//                val notification = JSONObject()
//                val notifcationBody = JSONObject()
//                try {
//                    notifcationBody.put("title", " Accept Task")
//                    notifcationBody.put("message", "you request has been accepted")
//                    notification.put("to", topic)
//                    notification.put("data", notifcationBody)
//                    Log.e("TAG", "try")
//
//                } catch (e: JSONException) {
//                    Log.e("TAG", "OnCreate: " + e.message)
//                }
//                sendNotification(notification)
//            }
        }
        btnViewReject.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reject Booking")
            builder.setMessage("Are you sure, you want to Reject??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        withContext(Dispatchers.Main) {
                            val offer = intent.getParcelableExtra<Description>("MyOffer")
                            val descriptionRepository = DescriptionRepository()
                            val response =
                                descriptionRepository.deleteDescription(offer!!._id!!)
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@JobNotificationActivity,
                                        "Booking Rejected",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@JobNotificationActivity,
                                ex.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                builder.setNegativeButton("No") { _, _ ->
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
//            if (!TextUtils.isEmpty(binding.tvViewName.text)) {
//                val topic = "/topics/Enter_topic"
//                val notification = JSONObject()
//                val notifcationBody = JSONObject()
//                try {
//                    notifcationBody.put("title", " Notification")
//                    notifcationBody.put("message", "your request has been rejected")
//                    notification.put("to", topic)
//                    notification.put("data", notifcationBody)
//                    Log.e("TAG", "try")
//                } catch (e: JSONException) {
//                    Log.e("TAG", "onCreate: " + e.message)
//                }
//                sendNotification(notification)
//                }
            }

        }
    }

    //    private fun sendNotification(notification: JSONObject) {
//        Log.e("TAG", "sendNotification")
//        val jsonObjectRequest = object : JsonObjectRequest(FCM_API, notification,
//            Response.Listener<JSONObject> { response ->
//                Log.i("TAG", "onResponse: $response")
//                tvViewName.setText("")
//            },
//            Response.ErrorListener {
//                Toast.makeText(this@JobNotificationActivity, "Request error", Toast.LENGTH_LONG)
//                    .show()
//                Log.i("TAG", "onErrorResponse: Didn't work")
//            }) {
//
//            override fun getHeaders(): Map<String, String> {
//                val params = HashMap<String, String>()
//                params["Authorization"] = serverKey
//                params["Content-Type"] = contentType
//                return params
//            }
//        }
//        requestQueue.add(jsonObjectRequest)
//
//
//    }
    //    @SuppressLint("SetTextI18n")
//
    private fun getDescription() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val offer = intent.getParcelableExtra<Description>("MyOffer")
                val userRepository = UserRepository()
                val response = userRepository.getUserById("${offer!!.addedby}")
                withContext(Dispatchers.Main) {
                    if (response.success == true) {
                        tvViewName.text = "${response.data!!.fullName}"
                        name = "${response.data.fullName}"
                        tvViewTitle.text = "${offer.title}"
                        tvViewTaskDesc.text = "${offer.taskDescription}"
                        tvViewEstTime.text = "${offer.estimatedTime}"
                        tvViewEstPrice.text = "Rs.${offer.price} per hour."
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@JobNotificationActivity,
                        "Error : $ex", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}