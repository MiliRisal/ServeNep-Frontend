package com.example.servenep.UI.feedback

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.lang.Exception


class ViewFeedbackFragment : Fragment() {
    private lateinit var fab: FloatingActionButton
    private lateinit var feedbackmessage: RecyclerView
    private lateinit var swipeRefresh1: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_feedback, container, false)

        fab = view.findViewById(R.id.fab)
        feedbackmessage = view.findViewById(R.id.Feedbackmessage)
        swipeRefresh1 = view.findViewById(R.id.swipeRefresh1)

    fab.setOnClickListener {
        val intent = Intent(this@ViewFeedbackFragment.context, FeedbackActivity::class.java)
        startActivity(intent)
    }
    loadfeedback()
    dataloading()
    return view
    }

    private fun loadfeedback() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val feedbackRespository=FeedbackRespository()
                val response= feedbackRespository.allFeedback()
                if (response.data != null){
                    val lstfeedback = response.data
                    withContext(Dispatchers.Main){
                        feedbackmessage.layoutManager = LinearLayoutManager(activity)
                        feedbackmessage.adapter = FeedbackAdapter(requireContext(),lstfeedback)
                    }
                }

            }catch (ex:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(activity, "Error:${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun dataloading() {
        swipeRefresh1.setOnClickListener {
            loadfeedback()
            swipeRefresh1.isRefreshing=false
        }
    }


}

