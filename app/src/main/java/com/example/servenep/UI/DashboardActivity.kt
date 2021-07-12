package com.example.servenep.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import com.example.servenep.Category
import com.example.servenep.CategoryAdapter
import com.example.servenep.R

class DashboardActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemClickListener {
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

        gridView?.setOnItemClickListener(this)
    }
    private fun setDataList():ArrayList<Category>{
        var arrayList:ArrayList<Category> = ArrayList()
        arrayList.add(Category(R.drawable.cleaner,"Cleaner"))
        arrayList.add(Category(R.drawable.electric,"Electrician"))
        arrayList.add(Category(R.drawable.cleaner,"sweeper"))
        arrayList.add(Category(R.drawable.cleaner,"carpenter"))
        return arrayList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        startActivity(
            Intent(
                this@DashboardActivity,
                TaskDescriptionActivity::class.java
            )
        )
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}