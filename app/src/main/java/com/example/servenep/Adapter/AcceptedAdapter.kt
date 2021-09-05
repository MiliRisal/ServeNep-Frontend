package com.example.servenep.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.R
import com.example.servenep.UI.ViewTasksActivity
import com.example.servenep.entities.AcceptedTask

class AcceptedAdapter(
    val context: Context,
    val listAcceptedTask: MutableList<AcceptedTask>
) : RecyclerView.Adapter<AcceptedAdapter.AcceptedViewHolder>() {
    class AcceptedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAcceptedTaskTitle: TextView
        val tvAcceptedTaskDesc: TextView
        val tvAcceptedTime: TextView
        val tvAcceptedPrice: TextView
        val tvAcceptedMoreDetail: TextView
        val acceptedCard: CardView

        init {
            tvAcceptedTaskTitle = view.findViewById(R.id.tvAcceptedTaskTitle)
            tvAcceptedTaskDesc = view.findViewById(R.id.tvAcceptedTaskDesc)
            tvAcceptedTime = view.findViewById(R.id.tvAcceptedTime)
            tvAcceptedPrice = view.findViewById(R.id.tvAcceptedPrice)
            acceptedCard = view.findViewById(R.id.acceptedCard)
            tvAcceptedMoreDetail = view.findViewById(R.id.tvAcceptedMoreDetail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcceptedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.accepted_task, parent, false)
        return AcceptedViewHolder(view)
    }

    override fun onBindViewHolder(holder: AcceptedViewHolder, position: Int) {
        val lstTasks = listAcceptedTask[position]
        holder.tvAcceptedTaskTitle.text = lstTasks.descTitle
        holder.tvAcceptedTaskDesc.text = lstTasks.description
        holder.tvAcceptedTime.text = "Estimated Time: \n ${lstTasks.time}"
        holder.tvAcceptedPrice.text = "Estimated Price: \n Rs.${lstTasks.rate}"
        holder.acceptedCard.setOnClickListener {
            val intent = Intent(context, ViewTasksActivity::class.java)
            intent.putExtra("Task", lstTasks)
            context.startActivity(intent)
        }
        holder.tvAcceptedMoreDetail.setOnClickListener {
            val intent = Intent(context, ViewTasksActivity::class.java)
            intent.putExtra("Task", lstTasks)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listAcceptedTask.size
    }
}