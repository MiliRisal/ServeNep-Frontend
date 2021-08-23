package com.example.servenep.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.Adapter.DashboardAdapter
import com.example.servenep.R
import com.example.servenep.UI.TaskerRecyclerViewActivity
import com.example.servenep.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DashboardFragment : Fragment(), AdapterView.OnItemClickListener  {

    private val cat = arrayOf("---Choose category---","Cleaner","Electrician","Delivery","Carpenter","Plumber","Mechanic")
    private lateinit var spinner: Spinner
    private lateinit var categoryRecyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        spinner = view.findViewById(R.id.spinner)

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

        categoryRecyclerview = view.findViewById(R.id.categoryRecyclerview)
        allCategories()

        return view
    }

    private fun allCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categoryRepository = CategoryRepository()
                val response = categoryRepository.getAllCategory()

                if(response.success == true){
                    withContext(Dispatchers.Main){
                        val listCategory = response.data
                        categoryRecyclerview.adapter =
                            listCategory?.let { DashboardAdapter (requireContext(), it) }
                        categoryRecyclerview.layoutManager = LinearLayoutManager(context)
                    }
                }
            }
            catch (ex : Exception){


            }
        }
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