package com.example.servenep.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.entities.Category
import com.example.servenep.entities.Description
import com.example.servenep.entities.Users
import com.example.servenep.repository.DescriptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDescriptionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ettile: EditText
    private lateinit var ettaskdes: EditText
    private lateinit var rbminhour: RadioButton
    private lateinit var rbmidhour: RadioButton
    private lateinit var rbmaxhour: RadioButton
    private lateinit var etprice: EditText
    private lateinit var btnsubmit: Button
    private lateinit var backButtonFromAddDesc: ImageView
    private var category : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_description)

        ettile=findViewById(R.id.ettitle)
        ettaskdes=findViewById(R.id.ettaskdes)
        rbminhour=findViewById(R.id.rbminhour)
        rbmidhour=findViewById(R.id.rbmidhour)
        rbmaxhour=findViewById(R.id.rbmaxhour)
        etprice=findViewById(R.id.etprice)
        btnsubmit=findViewById(R.id.btnsubmit)
        backButtonFromAddDesc=findViewById(R.id.backButtonFromAddDesc)

        btnsubmit.setOnClickListener(this)
        backButtonFromAddDesc.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnsubmit -> {
                insertDescription()
            }
            R.id.backButtonFromAddDesc ->{
                val category = intent.getParcelableExtra<Users>("userDetail")
                val intent = Intent(this, TaskerRecyclerViewActivity::class.java)
                intent.putExtra("Category", category?.category.toString() )
                startActivity(intent)
            }
        }
    }

    private fun insertDescription() {
        val category = intent.getParcelableExtra<Users>("userDetail")
        val bookedUserId = category?._id.toString()
        val title = ettile.text.toString().trim()
        val taskDescription = ettaskdes.text.toString().trim()
        val price = etprice.text.toString().trim()
        var estimatedTime = ""
        when {
            rbminhour.isChecked -> {
                estimatedTime = "1-2 hrs"
            }
            rbmidhour.isChecked -> {
                estimatedTime = "2-3 hrs"
            }
            rbmaxhour.isChecked -> {
                estimatedTime = "3-more"
            }
        }

        if (title == "") {
            ettile.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }
        if (taskDescription == "") {
            ettaskdes.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }
        if (price == "") {
            etprice.error = "Fill up !!"
            Toast.makeText(this, "Cannot leave the fields empty !!", Toast.LENGTH_SHORT).show()
        }
        else {
            val description = Description(
                bookedUserId = bookedUserId,
                title = title,
                taskDescription = taskDescription,
                price = price,
                estimatedTime = estimatedTime
            )
            CoroutineScope(Dispatchers.IO).launch {
                // for API
                try {
                    val descriptionRepository = DescriptionRepository()
                    val response = descriptionRepository.descriptionInsert(description)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@TaskDescriptionActivity,
                                "Inserted Successfully!!", Toast.LENGTH_SHORT
                            ).show()
                        }
                        startActivity(
                            Intent(
                                this@TaskDescriptionActivity,
                                Home_Menu_Activity::class.java
                            )
                        )
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@TaskDescriptionActivity,
                            "error" + ex.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}