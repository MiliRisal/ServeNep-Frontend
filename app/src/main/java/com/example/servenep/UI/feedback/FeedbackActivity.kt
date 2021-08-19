package com.example.servenep.UI.feedback

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.servenep.R
import com.example.servenep.entities.Feedback
import com.example.servenep.repository.FeedbackRespository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class FeedbackActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etfeedtitle:EditText
    private lateinit var etfeeddescription:EditText
    private lateinit var btnSubmit:Button
    private lateinit var btncheck :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        etfeedtitle = findViewById(R.id.etfeedtitle)
        etfeeddescription = findViewById(R.id.etfeeddescription)
        btnSubmit = findViewById(R.id.btnSubmit)
        btncheck = findViewById(R.id.btnCheck)
        btnSubmit.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSubmit-> {
                insertFeedback ()
            }
        }
    }



    private fun insertFeedback() {
        val feedtitle = etfeedtitle.text.toString().trim()
        val feeddescription= etfeeddescription.text.toString()

        if (feedtitle ==""){
            etfeedtitle.error = "Fill up!!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }
        if(feeddescription ==""){
            etfeeddescription.error="Fill up!!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()

        }
        else {
            val feedback = Feedback(
                feedtitle = feedtitle,
                feeddescription = feeddescription
            )
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val feedbackRespository =FeedbackRespository()
                    val response= feedbackRespository.insertFeedback(feedback)
                    if(response.success==true){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@FeedbackActivity, "Feedback Insert Successfully", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    startActivity(
                        Intent(
                            this@FeedbackActivity, FeedbackActivity::class.java
                        )
                    )
                }
                catch (ex:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@FeedbackActivity, "error" + ex.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }


}