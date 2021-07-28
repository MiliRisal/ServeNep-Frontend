package com.example.servenep.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.R
import com.example.servenep.entities.Users
import de.hdodenhof.circleimageview.CircleImageView

class TaskerAdapter(
    private val lstTasker: List<Users>
): RecyclerView.Adapter<TaskerAdapter.TaskerViewHolder>(){

    private var imgProfile= intArrayOf(R.drawable.girl,R.drawable.girl,R.drawable.girl,R.drawable.girl )

    class TaskerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgProfile: CircleImageView
        val tvName: TextView
        val tvArea: TextView
        val tvPrice: TextView
        val tvCategory: TextView

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

    override fun onBindViewHolder(holder: TaskerViewHolder, position: Int) {
        holder.imgProfile.setImageResource(imgProfile[position])
        val tasker = lstTasker[position]
        holder.tvName.text=tasker.fullName
        holder.tvCategory.text=tasker.category
        holder.tvArea.text=tasker.address
        holder.tvPrice.text= tasker.price.toString()
    }

    override fun getItemCount(): Int {
        return  lstTasker.size
    }

}

