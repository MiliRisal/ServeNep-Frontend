package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import com.example.servenep.Category
import com.example.servenep.CategoryAdapter
import com.example.servenep.R

class DashboardActivity : AppCompatActivity() {
    private lateinit var homeicon:ImageView
    private lateinit var profileicon:ImageView
    private lateinit var toolsicon: ImageView
    private var gridView:GridView ?=null
    private var arrayList:ArrayList<Category> ?=null
    private var CategoryAdapter:CategoryAdapter ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        homeicon=findViewById(R.id.homeicon)
        profileicon=findViewById(R.id.profileicon)
        toolsicon=findViewById(R.id.toolsicon)


        gridView=findViewById(R.id.gridview)
        arrayList= ArrayList()
        arrayList=setDataList()
        CategoryAdapter= CategoryAdapter(applicationContext, arrayList!!)
        gridView?.adapter=CategoryAdapter


    }
    private fun setDataList():ArrayList<Category>{
        var arrayList:ArrayList<Category> = ArrayList()
        arrayList.add(Category(R.drawable.cleaner,"Cleaner"))
        arrayList.add(Category(R.drawable.electric,"Electrician"))
        arrayList.add(Category(R.drawable.cleaner,"sweeper"))
        arrayList.add(Category(R.drawable.cleaner,"carpenter"))
        return arrayList
    }
}