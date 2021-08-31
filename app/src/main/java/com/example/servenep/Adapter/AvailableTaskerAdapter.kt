package com.example.servenep.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.servenep.Home_Menu_Activity
import com.example.servenep.R
import com.example.servenep.UI.TaskDescriptionActivity
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.entities.Category
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
        init {
            userProfileImage= view.findViewById(R.id.userProfileImage)
            txtUserName=view.findViewById(R.id.txtUserName)
            txtUserAddress=view.findViewById(R.id.txtUserAddress)
            btnBookUser=view.findViewById(R.id.btnBookUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableTaskerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tasker, parent, false)
        return AvailableTaskerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvailableTaskerViewHolder, position: Int) {
        val tasker = listTasker[position]
        holder.txtUserName.text=tasker.fullName
        holder.txtUserAddress.text=tasker.address

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

    override fun getItemCount(): Int {
        return listTasker.size
    }
}