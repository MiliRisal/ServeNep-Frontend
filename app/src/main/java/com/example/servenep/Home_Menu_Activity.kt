package com.example.servenep


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.servenep.UI.LoginActivity
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.databinding.ActivityHomeMenuBinding
import com.example.servenep.repository.UserRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home_Menu_Activity : AppCompatActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_menu)

        if (!hasPermission()) {
            requestPermission()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_content_home_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val hView = navView.getHeaderView(0)
        val textViewName = hView.findViewById(R.id.textViewName) as TextView
        val textViewEmail = hView.findViewById(R.id.textViewEmail) as TextView
        val textuser = hView.findViewById(R.id.textuser) as TextView
        val navImage = hView.findViewById(R.id.imageView) as ImageView
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.getUser()
                if(response.success == true){
                    val  user = response.data!!
                    withContext(Dispatchers.Main) {
                        val image = ServiceBuilder.loadImagePath() + user.profileImage
                        textViewName.setText("${user.fullName}")
                        textViewEmail.setText("${user.email}")
                        textuser.setText("${user.role}")
                        if (!user.profileImage.equals("")) {
                            Glide.with(this@Home_Menu_Activity)
                                .load(image)
                                .into(navImage)
                        }
                    }
                }

            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        this@Home_Menu_Activity,
                        "Error : ${ex}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard,
                R.id.nav_MyOffers,
                R.id.nav_MyBookings,
                R.id.nav_AcceptedTasks,
                R.id.nav_profile,
                R.id.nav_AboutUs
            ), drawerLayout
        )
        if (ServiceBuilder.usertype == "Tasker"){
            navView.menu.removeItem(R.id.nav_MyBookings)
        }
        if(ServiceBuilder.usertype == "Customer"){
            navView.menu.removeItem(R.id.nav_MyOffers)
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener { item ->
            when (item.itemId){
                R.id.nav_logout -> {
                    getSharedPref()
                    ServiceBuilder.token.equals("")
                    ServiceBuilder.id.equals("")
                    ServiceBuilder.usertype.equals("")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun getSharedPref() {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@Home_Menu_Activity,
            permissions, 1
        )

    }

    private fun hasPermission():Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home__menu_, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}