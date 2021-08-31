package com.example.servenep.UI.acceptedTasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.Adapter.AcceptedAdapter
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.repository.AcceptedTaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AcceptedTasksFragment : Fragment() {

    private lateinit var myAcceptedRecyclerview : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_accepted_tasks, container, false)
        myAcceptedRecyclerview = view.findViewById(R.id.myAcceptedRecyclerview)
        acceptedTask()
        return view
    }

    private fun acceptedTask() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if(ServiceBuilder.usertype == "Tasker"){
                    val acceptedTaskRepository = AcceptedTaskRepository()
                    val response1 = acceptedTaskRepository.GetTaskAcceptedByTasker(ServiceBuilder.id!!)
                    val listAcceptedTask = response1.data
                    if (response1.success == true){
                        withContext(Dispatchers.Main){
                            myAcceptedRecyclerview.adapter =
                                listAcceptedTask?.let { AcceptedAdapter(requireContext(), it) }
                            myAcceptedRecyclerview.layoutManager = LinearLayoutManager(requireContext())
                        }
                    }
                }
                if(ServiceBuilder.usertype == "Customer"){
                    val acceptedTaskRepository = AcceptedTaskRepository()
                    val response2 = acceptedTaskRepository.GetTaskAcceptedByUser(ServiceBuilder.id!!)
                    val listAcceptedTask1 = response2.data
                    if (response2.success == true){
                        withContext(Dispatchers.Main){
                            myAcceptedRecyclerview.adapter =
                                listAcceptedTask1?.let { AcceptedAdapter(requireContext(), it) }
                            myAcceptedRecyclerview.layoutManager = LinearLayoutManager(requireContext())
                        }
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error : ${ex}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}