package com.example.servenep.UI.myOffers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.Adapter.AvailableTaskerAdapter
import com.example.servenep.Adapter.MyOffersAdapter
import com.example.servenep.R
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.repository.DescriptionRepository
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyOffersFragment : Fragment() {
    private lateinit var myOffersRecyclerview : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myoffers, container, false)
        myOffersRecyclerview = view.findViewById(R.id.myOffersRecyclerview)
        myOffers()
        return view
    }

    private fun myOffers() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val descriptionRepository = DescriptionRepository()
                val response = descriptionRepository.allDescriptionByBookedUserId(ServiceBuilder.id!!)
                val lstOffers = response.data
                if(response.success == true){
                    withContext(Dispatchers.Main){
                        myOffersRecyclerview.adapter =
                            lstOffers?.let { MyOffersAdapter(requireContext(), it)}
                        myOffersRecyclerview.layoutManager = LinearLayoutManager(requireContext())
                    }
                }

            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,
                        "Error : ${ex}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}