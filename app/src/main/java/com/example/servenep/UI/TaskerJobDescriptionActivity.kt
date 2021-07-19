package com.example.servenep.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.servenep.R
import com.example.servenep.entities.TaskerSpecification
import com.example.servenep.entities.Users
import com.example.servenep.repository.SpecificationRepository
import com.example.servenep.repository.UserRepository
import com.example.servenep.response.SpecificationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskerJobDescriptionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etName:EditText
    private val category = arrayOf("Cleaner","Electrician","Delivery","Carpenter","Plumber","Mechanic")
    private lateinit var selectedcat : String
    private lateinit var spTaskerCategory: Spinner
    private lateinit var etRate:EditText
    private lateinit var etArea:EditText
    private lateinit var btnTaskerSubmit:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasker_job_description)

        etName=findViewById(R.id.etName)
        spTaskerCategory=findViewById(R.id.spTaskerCategory)
        etRate=findViewById(R.id.etRate)
        etArea=findViewById(R.id.etArea)
        btnTaskerSubmit=findViewById(R.id.btnTaskerSubmit)

        btnTaskerSubmit.setOnClickListener(this)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            category
        )

        spTaskerCategory.adapter=adapter
        spTaskerCategory.onItemSelectedListener=
            object :AdapterView.OnItemSelectedListener{

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedcat = parent?.getItemAtPosition(position).toString()

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnTaskerSubmit->{
                addspecification()
            }
        }
    }

    private fun addspecification() {
        val name = etName.text.toString().trim()
        val rate = etRate.text.toString().toInt()
        val area = etArea.text.toString().trim()

        var category = selectedcat

        if (name == "") {
            etName.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }
        if (rate == null) {
            etRate.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }
        if (area == "") {
            etArea.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }else{
            //APi starts
            val specifications= TaskerSpecification(
                name = name,
                price = rate,
                area = area,
                category = category
            )
            CoroutineScope(Dispatchers.IO).launch {
                // for API
                try {
                    val specificationRepository = SpecificationRepository()
                    val response = specificationRepository.specification(specifications)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@TaskerJobDescriptionActivity,
                                "Added Successfully!!", Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@TaskerJobDescriptionActivity, "error" + ex.toString(), Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }
    }
}