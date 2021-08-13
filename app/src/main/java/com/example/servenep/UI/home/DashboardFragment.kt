package com.example.servenep.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.servenep.Category
import com.example.servenep.CategoryAdapter
import com.example.servenep.R
import com.example.servenep.UI.TaskDescriptionActivity
import com.example.servenep.UI.TaskerRecyclerViewActivity

class DashboardFragment : Fragment(), AdapterView.OnItemClickListener  {

    private val cat = arrayOf("---Choose category---","Cleaner","Electrician","Delivery","Carpenter","Plumber","Mechanic")
    private lateinit var spinner: Spinner
    private lateinit var homeicon: ImageView
    private lateinit var profileicon: ImageView
    private lateinit var toolsicon: ImageView
    private var gridView: GridView?=null
    private var arrayList:ArrayList<Category> ?=null
    private var CategoryAdapter: CategoryAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
       // homeicon=view.findViewById(R.id.homeicon)
       // profileicon=view.findViewById(R.id.profileicon)
       // toolsicon=view.findViewById(R.id.toolsicon)
        gridView=view.findViewById(R.id.gridview)
        spinner = view.findViewById(R.id.spinner)

        arrayList= ArrayList()
        arrayList=setDataList()
        CategoryAdapter= CategoryAdapter(view.context, arrayList!!)
        gridView?.adapter=CategoryAdapter

        gridView?.setOnItemClickListener(this)
        //array apdater for items
        val adapter = object: ArrayAdapter<String>(
            requireContext(),
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
            object: AdapterView.OnItemSelectedListener{

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    startActivity(
//                        Intent(
//                            context,
//                            TaskDescriptionActivity::class.java
//                        )
//                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        return view
    }
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
                context,
                TaskerRecyclerViewActivity::class.java
            )
        )
    }
}