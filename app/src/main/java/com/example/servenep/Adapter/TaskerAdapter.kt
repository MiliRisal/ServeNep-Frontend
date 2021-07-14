package com.example.servenep.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.servenep.R
import com.example.servenep.model.Tasker
import de.hdodenhof.circleimageview.CircleImageView

class TaskerAdapter (): RecyclerView.Adapter<TaskerAdapter.TaskerViewHolder>(){

    private var imgProfile= intArrayOf(R.drawable.girl,R.drawable.girl,R.drawable.girl,R.drawable.girl )
    private var tvName= arrayOf("Rita Rimal","Rita Rimal","Rita Rimal","Rita Rimal")
    private var tvCategory= arrayOf("Cleaner","Cleaner","Cleaner","Cleaner")
    private var tvArea = arrayOf("Koteshwor","Koteshwor","Koteshwor","Koteshwor")
    private var tvPrice= arrayOf("100 per.hr","100 per.hr","100 per.hr","100 per.hr")

    class TaskerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgProfile: CircleImageView
        val tvName: TextView
        val tvCategory: TextView
        val tvArea: TextView
        val tvPrice: TextView

        init {
            imgProfile= view.findViewById(R.id.imgProfile)
            tvName=view.findViewById(R.id.tvName)
            tvCategory=view.findViewById(R.id.tvCategory)
            tvArea=view.findViewById(R.id.tvArea)
            tvPrice=view.findViewById(R.id.tvPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskerViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.tasker, parent, false)
        return TaskerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  imgProfile.size

    }

    override fun onBindViewHolder(holder: TaskerViewHolder, position: Int) {
       holder.imgProfile.setImageResource(imgProfile[position])
        holder.tvName.text=tvName[position]
        holder.tvCategory.text=tvCategory[position]
        holder.tvArea.text=tvArea[position]
        holder.tvPrice.text=tvPrice[position]


    }
}

