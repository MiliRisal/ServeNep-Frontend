package com.example.servenep.UI.aboutUS

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.servenep.R

class AboutUsFragment : Fragment() {
    private lateinit var map : ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_us, container, false)
        map = view.findViewById(R.id.map)

        map.setOnClickListener {
            startActivity(Intent(requireContext(), OfficeMapsActivity::class.java))
        }

        return view
    }
}