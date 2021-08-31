package com.example.servenep.UI.feedback

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.servenep.Adapter.FeedbackAdapter
import com.example.servenep.R
import com.example.servenep.repository.FeedbackRespository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewFeedbackActivity : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var feedbackmessage: RecyclerView
    private lateinit var swipeRefresh1: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_feedback)


        fab =findViewById(R.id.fab)
        feedbackmessage =findViewById(R.id.Feedbackmessage)
        swipeRefresh1 =findViewById(R.id.swipeRefresh1)
        loadfeedback()

        fab.setOnClickListener {
            val intent =Intent(this@ViewFeedbackActivity, FeedbackActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loadfeedback() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val feedbackRespository = FeedbackRespository()
                val response =  feedbackRespository.allFeedback()
                if (response.success ==true){
                    val lstfeedback = response.data
                    withContext(Dispatchers.Main) {
                        feedbackmessage.adapter =  FeedbackAdapter(this@ViewFeedbackActivity,lstfeedback!!)
                        feedbackmessage.layoutManager= LinearLayoutManager(this@ViewFeedbackActivity)                    }
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ViewFeedbackActivity, "Error:${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}


