package com.example.servenep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.example.servenep.R

class TaskDescriptionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ettile: EditText
    private lateinit var ettaskdes: EditText
    private lateinit var rbminhour: RadioButton
    private lateinit var rbmidhour: RadioButton
    private lateinit var rbmaxhour: RadioButton
    private lateinit var etprice: EditText
    private lateinit var btnsubmit: Button


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

        btnsubmit.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}