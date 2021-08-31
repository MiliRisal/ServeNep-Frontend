package com.example.servenep.UI.myBookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.Adapter.MyBookingsAdapter
import com.example.servenep.Adapter.MyOffersAdapter
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.repository.DescriptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyBookingsFragment : Fragment() {
    private lateinit var myBookingsRecyclerview : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mybookings, container, false)
        myBookingsRecyclerview = view.findViewById(R.id.myBookingsRecyclerview)
        myBookings()
        return view
    }

    private fun myBookings() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val descriptionRepository = DescriptionRepository()
                val response = descriptionRepository.allDescriptionByAddedBy(ServiceBuilder.id!!)
                val lstBookings = response.data
                if(response.success == true){
                    withContext(Dispatchers.Main){
                        myBookingsRecyclerview.adapter =
                            lstBookings?.let { MyBookingsAdapter(requireContext(), it) }
                        myBookingsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),
                        "Error : ${ex}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}