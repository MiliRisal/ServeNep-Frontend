package com.example.servenep.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.servenep.Category
import com.example.servenep.CategoryAdapter
import com.example.servenep.R

class DashboardActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {
    private val cat = arrayOf("---Choose category---","Cleaner","Electrician","Delivery","Carpenter","Plumber","Mechanic")
    private lateinit var spinner: Spinner

    private lateinit var homeicon:ImageView
    private lateinit var profileicon:ImageView
    private lateinit var toolsicon: ImageView
    private lateinit var notificationicon: ImageView
    private var gridView:GridView ?=null
    private var arrayList:ArrayList<Category> ?=null
    private var CategoryAdapter:CategoryAdapter ?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        homeicon=findViewById(R.id.homeicon)
        profileicon=findViewById(R.id.profileicon)
        toolsicon=findViewById(R.id.toolsicon)
        notificationicon=findViewById(R.id.notificationicon)

        gridView=findViewById(R.id.gridview)
        spinner = findViewById(R.id.spinner)

        arrayList= ArrayList()
        arrayList=setDataList()
        CategoryAdapter= CategoryAdapter(applicationContext, arrayList!!)
        gridView?.adapter=CategoryAdapter

        gridView?.setOnItemClickListener(this)
        notificationicon.setOnClickListener(this)

        //array apdater for items
        val adapter = object: ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            cat){
            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }
        }

        //setting adapter to spinner adapter
        spinner.adapter = adapter
        //on item listener
        spinner.onItemSelectedListener=
            object:AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                startActivity(
                    Intent(
                        this@DashboardActivity,
                        TaskDescriptionActivity::class.java
                    )
                )
            }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }


    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.nav_menu,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.abc->Toast.makeText(applicationContext,"abc clicked",Toast.LENGTH_SHORT)
//            R.id.cde->Toast.makeText(applicationContext,"cde clicked",Toast.LENGTH_SHORT)
//            R.id.fgh->Toast.makeText(applicationContext,"fgh clicked",Toast.LENGTH_SHORT)
//            R.id.ijk->Toast.makeText(applicationContext,"ijk clicked", Toast.LENGTH_SHORT)
//
//        }
//        return super.onOptionsItemSelected(item)
//    }
    private fun setDataList():ArrayList<Category>{
        var arrayList:ArrayList<Category> = ArrayList()
        arrayList.add(Category(R.drawable.cleaner,"Cleaner"))
        arrayList.add(Category(R.drawable.electric,"Electrician"))
        arrayList.add(Category(R.drawable.delivery,"Delivery"))
        arrayList.add(Category(R.drawable.carpenter,"Carpenter"))
        arrayList.add(Category(R.drawable.plumber,"Plumber"))
        arrayList.add(Category(R.drawable.mechanic,"Mechanic"))
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