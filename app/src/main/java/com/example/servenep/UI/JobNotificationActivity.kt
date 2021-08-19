package com.example.servenep.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.servenep.R
import com.example.servenep.databinding.ActivityJobNotificationBinding
import com.example.servenep.databinding.ActivityTaskerBookingBinding
import com.example.servenep.repository.DescriptionRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class JobNotificationActivity : AppCompatActivity() {

    private val FCM_API="https://fcm.googleapis.com/fcm/send"
    private val serverKey= "key="+ "AAAABfaM5Us:APA91bFaJ928p1v8x9JORngHk7Kd-4AHeIWPKfn8CM8W8Z7zFA1NhBYEcK9cPvQMRUByc47Y_UmUd6iEOL5wMoFv-4g91FH2A84YQWcjIYHkBbZ6o_9JODoDaMahjREXONvrJJnmbMrG "
    private val contentType = "application/json"
    private lateinit var tvName: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvArea: TextView
    private lateinit var tvEstTime: TextView
    private lateinit var tvEstRate: TextView
    private lateinit var btnAccept:Button
    private lateinit var btnReject:Button

    private val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(this.applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_notification)
        val binding = DataBindingUtil.setContentView<ActivityJobNotificationBinding>(this, R.layout.activity_job_notification
        )

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/Enter_topic")
        tvName = findViewById(R.id.tvName)
        tvTitle = findViewById(R.id.tvCategory)
        tvArea = findViewById(R.id.tvArea)
        tvEstTime = findViewById(R.id.tvEstTime)
        tvEstRate = findViewById(R.id.tvEstRate)
        btnAccept = findViewById(R.id.btnAccept)
        btnReject = findViewById(R.id.btnReject)

        getDescription()
        btnAccept.setOnClickListener { if(!TextUtils.isEmpty(binding.tvName.text)) {
            val topic="/topics/Enter_topic"
            val notification=JSONObject()
            val notifcationBody=JSONObject()
            try {
                notifcationBody.put("title", " Accept Task")
                notifcationBody.put("message", binding.tvName.text)
                notification.put("to", topic)
                notification.put("data", notifcationBody)
                Log.e("TAG", "try")

            }catch (e:JSONException){
                Log.e("TAG","OnCreate: " +e.message)
            }
            sendNotification(notification)
        } }
        btnReject.setOnClickListener { if(!TextUtils.isEmpty(binding.tvName.text)) {
            val topic = "/topics/Enter_topic"

            val notification = JSONObject()
            val notifcationBody = JSONObject()
            try {
                notifcationBody.put("title", " Notification")
                notifcationBody.put("message", "your request has been rejected")
                notification.put("to", topic)
                notification.put("data", notifcationBody)
                Log.e("TAG", "try")
            } catch (e: JSONException) {
                Log.e("TAG", "onCreate: " + e.message)
            }
            sendNotification(notification)

        } }    }

    private fun sendNotification(notification: JSONObject) {
        Log.e("TAG", "sendNotification")
        val jsonObjectRequest = object : JsonObjectRequest(FCM_API, notification,
            Response.Listener<JSONObject> { response ->
                Log.i("TAG", "onResponse: $response")
                tvName.setText("")
            },
            Response.ErrorListener {
                Toast.makeText(this@JobNotificationActivity, "Request error", Toast.LENGTH_LONG).show()
                Log.i("TAG", "onErrorResponse: Didn't work")
            }) {

            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = serverKey
                params["Content-Type"] = contentType
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)


    }

    @SuppressLint("SetTextI18n")
    private fun getDescription() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val descriptionRepository = DescriptionRepository()
                val response = descriptionRepository.allTaskDescription()
                withContext(Dispatchers.Main) {
                    if (response.success == true) {
                        val task = response.data!!
                        tvTitle.text = "${task[0].title}"
                        tvArea.text = "${task[0].taskDescription}"
                        tvEstTime.text = "Estimated time is ${task[0].estimatedTime}"
                        tvEstRate.text = "Rs.${task[0].price} per hour."
                    }
                }

            }catch (ex: Exception) {
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