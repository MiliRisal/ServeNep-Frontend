package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.servenep.R
import com.example.servenep.databinding.ActivityTaskerBookingBinding

import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class TaskerBookingActivity : AppCompatActivity() {
    private val FCM_API="https://fcm.googleapis.com/fcm/send"
    private val serverKey= "key="+ "AAAABfaM5Us:APA91bFaJ928p1v8x9JORngHk7Kd-4AHeIWPKfn8CM8W8Z7zFA1NhBYEcK9cPvQMRUByc47Y_UmUd6iEOL5wMoFv-4g91FH2A84YQWcjIYHkBbZ6o_9JODoDaMahjREXONvrJJnmbMrG "
    private val contentType = "application/json"
    private lateinit var imgProfile:ImageView
    private lateinit var tvName:TextView
    private lateinit var tvCategory:TextView
    private lateinit var tvDesc:TextView
    private lateinit var tvestRate:TextView
    private lateinit var IconBook:ImageView
    private lateinit var tvBook:TextView

    private val requestQueue : RequestQueue by lazy {
        Volley.newRequestQueue(this.applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_booking)
        val binding = DataBindingUtil.setContentView<ActivityTaskerBookingBinding>(this, R.layout.activity_tasker_booking )

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/Enter_topic")


        imgProfile=findViewById(R.id.imgProfile)
        tvName=findViewById(R.id.tvName)
        tvCategory=findViewById(R.id.tvCategory)
        tvDesc=findViewById(R.id.tvDesc)
        tvestRate=findViewById(R.id.tvestRate)
        IconBook=findViewById(R.id.IconBook)
        tvBook=findViewById(R.id.tvBook)


        tvBook.setOnClickListener{
            if (!TextUtils.isEmpty(binding.tvName.text)) {
                val topic = "/topics/Enter_topic" //topic has to match what the receiver subscribed to

                val notification = JSONObject()
                val notifcationBody = JSONObject()

                try {
                    notifcationBody.put("title", "Firebase Notification")
                    notifcationBody.put("message", binding.tvName.text)
                    notification.put("to", topic)
                    notification.put("data", notifcationBody)
                    Log.e("TAG", "try")
                } catch (e: JSONException) {
                    Log.e("TAG", "onCreate: " + e.message)
                }
                sendNotification(notification)
            }
        }
    }

    private fun sendNotification(notification: JSONObject) {
        Log.e("TAG", "sendNotification")
        val jsonObjectRequest = object : JsonObjectRequest(FCM_API, notification,
            Response.Listener<JSONObject> { response ->
                Log.i("TAG", "onResponse: $response")
                tvName.setText("")
            },
            Response.ErrorListener {
                Toast.makeText(this@TaskerBookingActivity, "Request error", Toast.LENGTH_LONG).show()
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

}
