package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.Adapter.TaskerAdapter
import com.example.servenep.R

class RecyclerviewActivity : AppCompatActivity() {
    private lateinit var recyclerview:RecyclerView

    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter:RecyclerView.Adapter<TaskerAdapter.TaskerViewHolder>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        recyclerview=findViewById(R.id.recyclerview)

        layoutManager=LinearLayoutManager(this)
        recyclerview.layoutManager=layoutManager

        adapter=TaskerAdapter()
        recyclerview.adapter=adapter
    }
}