package com.example.servenep.UI.myOffers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.R

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

    }
}