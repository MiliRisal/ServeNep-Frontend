package com.example.servenep.UI.location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.servenep.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.servenep.databinding.ActivityViewLocationBinding
import com.example.servenep.entities.AcceptedTask
import com.example.servenep.entities.Description
import com.example.servenep.entities.Users

class ViewLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityViewLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val name = intent.getStringExtra("name")
        val Accepted = intent.getStringExtra("Accepted")
        if (Accepted == "Accepted"){
            val location = intent.getParcelableExtra<AcceptedTask>("location")
            val latitude = location!!.latitude!!.toDouble()
            val longitude = location.longitude!!.toDouble()

            mMap = googleMap
            // Add a marker in myLocation and move the camera
            val myLocation = LatLng(latitude, longitude)
            mMap.addMarker(MarkerOptions().position(myLocation).title("${name}"))
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(myLocation, 13F), 3500, null
            )
            mMap.uiSettings.isZoomControlsEnabled = true
        }
        else{
            val location = intent.getParcelableExtra<Description>("location")
            val latitude = location!!.latitude!!.toDouble()
            val longitude = location.longitude!!.toDouble()

            mMap = googleMap
            // Add a marker in myLocation and move the camera
            val myLocation = LatLng(latitude, longitude)
            mMap.addMarker(MarkerOptions().position(myLocation).title("${name}"))
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(myLocation, 13F), 3500, null
            )
            mMap.uiSettings.isZoomControlsEnabled = true
        }

    }
}