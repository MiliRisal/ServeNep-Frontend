package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.abc->Toast.makeText(applicationContext,"abc clicked",Toast.LENGTH_SHORT)
            R.id.cde->Toast.makeText(applicationContext,"cde clicked",Toast.LENGTH_SHORT)
            R.id.fgh->Toast.makeText(applicationContext,"fgh clicked",Toast.LENGTH_SHORT)
            R.id.ijk->Toast.makeText(applicationContext,"ijk clicked",Toast.LENGTH_SHORT)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDataList():ArrayList<Category>{
        var arrayList:ArrayList<Category> = ArrayList()
        arrayList.add(Category(R.drawable.cleaner,"Cleaner"))
        arrayList.add(Category(R.drawable.electric,"Electrician"))
        arrayList.add(Category(R.drawable.delivery,"Delivery"))
        arrayList.add(Category(R.drawable.carpenter,"carpenter"))
        arrayList.add(Category(R.drawable.plumber,"Plumber"))
        arrayList.add(Category(R.drawable.mechanic,"Mechanic"))
        return arrayList
    }
}