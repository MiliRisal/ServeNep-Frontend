package com.example.servenep.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.servenep.R
import com.example.servenep.entities.Feedback
import com.example.servenep.repository.FeedbackRespository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class FeedbackAdapter (
    private val context:Context,
    private val lstFeedback:MutableList<Feedback>
):RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>(){

    class FeedbackViewHolder(view:View):RecyclerView.ViewHolder(view) {

            val feedbackTitle:TextView =view.findViewById(R.id.tvFeedbackTitle)
            val feedbackDescription:TextView =view.findViewById(R.id.tvFeedbackDescription)
           val feedbackdelete: ImageButton =view.findViewById(R.id.imageDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view =LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_feedback_layout, parent, false)
        return FeedbackViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val feedback = lstFeedback[position]
        holder.feedbackTitle.text = feedback.feedtitle
        holder.feedbackDescription.text = feedback.feeddescription

        holder.feedbackdelete.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete ${feedback.feedtitle}")
            builder.setMessage("Are you sure want to delete ${feedback.feedtitle} ??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("yes") { _, _ ->
                deleteFeedback(feedback)
            }
            builder.setNegativeButton("No") { _, _ ->
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }

            val alertDialog:AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
    }
    private fun deleteFeedback(feedback: Feedback){
        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main){
                Toast.makeText(context, "${feedback.feedtitle}", Toast.LENGTH_SHORT).show()
            }
            try {

                val feedbackRepository =FeedbackRespository()
                val response = feedbackRepository.DeleteFeedback(feedback._id!!)
                if (response.success ==true){
                    withContext(Dispatchers.Main){
                        lstFeedback.remove(feedback)
                        notifyDataSetChanged()
                    }
                }
            }catch (ex:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Error ${ex.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }

        }
  }


    override fun getItemCount(): Int {
        return lstFeedback.size
    }

}