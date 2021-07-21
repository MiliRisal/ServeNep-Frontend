package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.servenep.R
import com.example.servenep.repository.DescriptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobNotificationActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvArea: TextView
    private lateinit var tvestTime: TextView
    private lateinit var tvestRate: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_notification)
        tvName = findViewById(R.id.tvName)
        tvTitle = findViewById(R.id.tvCategory)
        tvArea = findViewById(R.id.tvArea)
        tvestTime = findViewById(R.id.tvestTime)
        tvestRate = findViewById(R.id.tvestRate)

        getDescription()
    }

    private fun getDescription() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val descriptionRepository = DescriptionRepository()
                val response = descriptionRepository.allTaskDescription()
                withContext(Dispatchers.Main) {
                    if (response.success == true) {
                        val task = response.data!!
                        tvTitle.setText("${task[0].title}")
                        tvArea.setText("${task[0].taskDescription}")
                        tvestTime.setText("Estimated time is ${task[0].estimatedTime}")
                        tvestRate.setText("Rs.${task[0].price} per hour.")
                    }
                }

            }catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@JobNotificationActivity,
                        "Error : ${ex}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}