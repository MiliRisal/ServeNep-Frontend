package com.example.servenep.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.servenep.R
import com.example.servenep.UI.TaskDescriptionActivity
import com.example.servenep.UI.profile.ViewProfileActivity
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.entities.Users
import de.hdodenhof.circleimageview.CircleImageView

class AvailableTaskerAdapter(
    val context: Context,
    private val listTasker : MutableList<Users>
): RecyclerView.Adapter<AvailableTaskerAdapter.AvailableTaskerViewHolder>() {
    class AvailableTaskerViewHolder(view : View): RecyclerView.ViewHolder(view){
        val userProfileImage : CircleImageView
        val txtUserName : TextView
        val txtUserAddress : TextView
        val btnBookUser : TextView
        val btnViewUserData : TextView
        val taskerCard : CardView
        init {
            userProfileImage= view.findViewById(R.id.userProfileImage)
            txtUserName=view.findViewById(R.id.txtUserName)
            txtUserAddress=view.findViewById(R.id.txtUserAddress)
            btnBookUser=view.findViewById(R.id.btnBookUser)
            btnViewUserData=view.findViewById(R.id.btnViewUserData)
            taskerCard=view.findViewById(R.id.taskerCard)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableTaskerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tasker, parent, false)
        return AvailableTaskerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvailableTaskerViewHolder, position: Int) {
        val tasker = listTasker[position]
        if(ServiceBuilder.usertype == "Customer"){
            holder.txtUserName.text=tasker.fullName
            holder.txtUserAddress.text=tasker.address
            holder.taskerCard.setOnClickListener {
                val intent = Intent(context, ViewProfileActivity::class.java)
                intent.putExtra("userDetail", tasker)
                context.startActivity(intent)
            }
            //load image with glide library
            val image = ServiceBuilder.loadImagePath() + tasker.profileImage
            if (!tasker.profileImage.equals("")) {
                Glide.with(context)
                    .load(image)
                    .into(holder.userProfileImage)
            }
            holder.btnBookUser.setOnClickListener{
                val intent = Intent(context, TaskDescriptionActivity::class.java)
                intent.putExtra("userDetail", tasker)
                context.startActivity(intent)

            }
        }
        if (ServiceBuilder.usertype == "Tasker"){
            holder.txtUserName.text=tasker.fullName
            holder.txtUserAddress.text=tasker.address
            holder.taskerCard.setOnClickListener {
                val intent = Intent(context, ViewProfileActivity::class.java)
                intent.putExtra("userDetail", tasker)
                context.startActivity(intent)
            }
            //load image with glide library
            val image = ServiceBuilder.loadImagePath() + tasker.profileImage
            if (!tasker.profileImage.equals("")) {
                Glide.with(context)
                    .load(image)
                    .into(holder.userProfileImage)
            }
            holder.btnBookUser.visibility = View.GONE
            holder.btnViewUserData.visibility = View.VISIBLE
            holder.btnViewUserData.setOnClickListener{
                val intent = Intent(context, ViewProfileActivity::class.java)
                intent.putExtra("userDetail", tasker)
                context.startActivity(intent)

            }
        }

    }

    override fun getItemCount(): Int {
        return listTasker.size
    }
}