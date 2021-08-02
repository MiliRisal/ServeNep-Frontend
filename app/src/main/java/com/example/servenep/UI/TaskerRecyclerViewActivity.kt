package com.example.servenep.UI

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.servenep.Adapter.TaskerAdapter
import com.example.servenep.R
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskerRecyclerViewActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerview:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_recyclerview)

        recyclerview=findViewById(R.id.recyclerview)
        loadTasker()

        //refresh layout
        swipeRefreshLayout.setOnRefreshListener {

            if(swipeRefreshLayout.isRefreshing){
                swipeRefreshLayout.setRefreshing(false)
            }
        }

    }

    private fun loadTasker() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val txt = intent.getStringExtra("category")
                val userRepository = UserRepository()
                val response = userRepository.getTaskerCategory(txt.toString())
                if(response.success == true){
                    val lstTasker = response.data
                    withContext(Dispatchers.Main){
                        recyclerview.adapter = TaskerAdapter( this@TaskerRecyclerViewActivity, lstTasker!!)
                        recyclerview.layoutManager = LinearLayoutManager(this@TaskerRecyclerViewActivity)
                    }
                }

            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@TaskerRecyclerViewActivity,
                        "Error : ${ex}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}