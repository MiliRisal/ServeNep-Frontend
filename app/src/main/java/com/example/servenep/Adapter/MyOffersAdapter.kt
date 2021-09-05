package com.example.servenep.Adapter

import android.app.AlertDialog
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
import com.example.servenep.UI.JobNotificationActivity
import com.example.servenep.UI.TaskDescriptionActivity
import com.example.servenep.entities.AcceptedTask
import com.example.servenep.entities.Description
import com.example.servenep.repository.AcceptedTaskRepository
import com.example.servenep.repository.DescriptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyOffersAdapter(
    val context: Context,
    val listMyOffers : MutableList<Description>
):RecyclerView.Adapter<MyOffersAdapter.MyOffersViewHolder>(){
    class MyOffersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTaskTitle: TextView
        val tvTaskDesc: TextView
        val tvMoreDetail: TextView
        val btnAccept: TextView
        val btnReject: TextView
        val myOffersCard: CardView

        init {
            tvTaskTitle = view.findViewById(R.id.tvTaskTitle)
            tvTaskDesc = view.findViewById(R.id.tvTaskDesc)
            tvMoreDetail = view.findViewById(R.id.tvMoreDetail)
            btnAccept = view.findViewById(R.id.btnAccept)
            btnReject = view.findViewById(R.id.btnReject)
            myOffersCard = view.findViewById(R.id.myOffersCard)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOffersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_offers, parent, false)
        return MyOffersViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOffersViewHolder, position: Int) {
        val MyOffers = listMyOffers[position]
            holder.tvTaskTitle.text = MyOffers.title
            holder.tvTaskDesc.text = MyOffers.taskDescription

            holder.tvMoreDetail.setOnClickListener {
                val intent = Intent(context, JobNotificationActivity::class.java)
                intent.putExtra("MyOffer", MyOffers)
                context.startActivity(intent)
            }
            holder.myOffersCard.setOnClickListener {
                val intent = Intent(context, JobNotificationActivity::class.java)
                intent.putExtra("MyOffer", MyOffers)
                context.startActivity(intent)
            }
            holder.btnAccept.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        withContext(Dispatchers.Main) {
                            val desc = AcceptedTask(
                                descTitle = MyOffers.title,
                                description = MyOffers.taskDescription,
                                rate = MyOffers.price,
                                time = MyOffers.estimatedTime,
                                longitude = MyOffers.longitude!!.toDouble(),
                                latitude = MyOffers.latitude!!.toDouble(),
                                acceptedby = MyOffers.bookedUserId,
                                userid = MyOffers.addedby.toString()
                            )
                            val acceptedTaskRepository = AcceptedTaskRepository()
                            val description = acceptedTaskRepository.insertAcceptedTask(desc)
                            if (description.success == true) {
                                val descriptionRepository = DescriptionRepository()
                                val response = descriptionRepository.deleteDescription(MyOffers._id!!)
                                if(response.success == true){
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            context,
                                            "Accepted", Toast.LENGTH_SHORT
                                        ).show()
                                        notifyDataSetChanged()
                                    }
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                ex.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        holder.btnReject.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Reject Booking")
            builder.setMessage("Are you sure, you want to Reject??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        withContext(Dispatchers.Main) {
                            val descriptionRepository = DescriptionRepository()
                            val response = descriptionRepository.deleteDescription(MyOffers._id!!)
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Booking Rejected",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                withContext(Dispatchers.Main) {
                                    listMyOffers.remove(MyOffers)
                                    notifyDataSetChanged()
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                ex.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return listMyOffers.size
    }
}