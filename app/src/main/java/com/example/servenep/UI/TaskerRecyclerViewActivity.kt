package com.example.servenep.UI

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.servenep.Adapter.AvailableTaskerAdapter
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.entities.Category
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskerRecyclerViewActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewUserRrecyclerview:RecyclerView
    private lateinit var backButtonFromUser:ImageView
    private lateinit var txtCategoryType:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_recyclerview)

        viewUserRrecyclerview=findViewById(R.id.viewUserRrecyclerview)
        backButtonFromUser=findViewById(R.id.backButtonFromUser)
        txtCategoryType=findViewById(R.id.txtCategoryType)

        backButtonFromUser.setOnClickListener{
            startActivity(Intent(this, Home_Menu_Activity::class.java))
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val category = intent.getStringExtra("Category")
                txtCategoryType.text = category.toString()
                val userRepository = UserRepository()
                val response = userRepository.getTaskerCategory(category.toString())
                if(response.success == true){
                    val lstTasker = response.data
                    withContext(Dispatchers.Main){
                        viewUserRrecyclerview.adapter =
                            lstTasker?.let { AvailableTaskerAdapter( this@TaskerRecyclerViewActivity, it) }
                        viewUserRrecyclerview.layoutManager = LinearLayoutManager(this@TaskerRecyclerViewActivity)
                    }
                }

            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@TaskerRecyclerViewActivity,
                        "Error : ${ex}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //refresh layout
//        swipeRefreshLayout.setOnRefreshListener {
//
//            if(swipeRefreshLayout.isRefreshing){
//                swipeRefreshLayout.setRefreshing(false)
//            }
//        }

    }


}