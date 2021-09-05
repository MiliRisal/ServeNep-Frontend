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
import com.example.servenep.UI.ViewTasksActivity
import com.example.servenep.UI.myBookings.UpdateBookingActivity
import com.example.servenep.entities.Description
import com.example.servenep.repository.DescriptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MyBookingsAdapter(
    val context: Context,
    val listMyBookings : MutableList<Description>
):RecyclerView.Adapter<MyBookingsAdapter.MyBookingsViewHolder>() {
    class MyBookingsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvBookingTaskTitle: TextView
        val tvBookingTaskDesc: TextView
        val tvBookingTime: TextView
        val tvBookingPrice: TextView
        val btnBookingUpdate: TextView
        val btnBookingCancel: TextView
        val bookingCardView: CardView
        init {
            tvBookingTaskTitle = view.findViewById(R.id.tvBookingTaskTitle)
            tvBookingTaskDesc = view.findViewById(R.id.tvBookingTaskDesc)
            tvBookingTime = view.findViewById(R.id.tvBookingTime)
            tvBookingPrice = view.findViewById(R.id.tvBookingPrice)
            btnBookingUpdate = view.findViewById(R.id.btnBookingUpdate)
            btnBookingCancel = view.findViewById(R.id.btnBookingCancel)
            bookingCardView = view.findViewById(R.id.bookingCardView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_bookings, parent, false)
        return MyBookingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyBookingsViewHolder, position: Int) {
        val MyBooking = listMyBookings[position]
        holder.tvBookingTaskTitle.text = MyBooking.title
        holder.tvBookingTaskDesc.text = MyBooking.taskDescription
        holder.tvBookingPrice.text =  "Estimated Price: \n Rs.${MyBooking.price}"
        holder.tvBookingTime.text = "Estimated Time: \n ${MyBooking.estimatedTime}"

        holder.bookingCardView.setOnClickListener {
            val intent = Intent(context, JobNotificationActivity::class.java)
            intent.putExtra("MyOffer", MyBooking)
            intent.putExtra("Booking", "Booking")
            context.startActivity(intent)
        }
        holder.btnBookingUpdate.setOnClickListener {
            val intent = Intent(context, UpdateBookingActivity::class.java)
            intent.putExtra("MyOffer", MyBooking)
            context.startActivity(intent)
        }

        holder.btnBookingCancel.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Cancel Booking")
            builder.setMessage("Are you sure, you want to cancel your booking??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        withContext(Dispatchers.Main) {
                            val descriptionRepository = DescriptionRepository()
                            val response = descriptionRepository.deleteDescription(MyBooking._id!!)
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Booking Cancel",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                withContext(Dispatchers.Main) {
                                    listMyBookings.remove(MyBooking)
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
        return listMyBookings.size
    }
}