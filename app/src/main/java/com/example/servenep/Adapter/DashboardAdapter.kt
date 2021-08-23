package com.example.servenep.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.servenep.R
import com.example.servenep.UI.TaskerRecyclerViewActivity
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.entities.Category

class DashboardAdapter(
    val context : Context,
    val listCategory : MutableList<Category>,
):RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>(){
    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgCategory : ImageView
        val txtCatTitle : TextView
        val txtCatDesc : TextView
        val txtViewTasker : TextView
        init {
            imgCategory = view.findViewById(R.id.imgCategory)
            txtCatTitle = view.findViewById(R.id.txtCatTitle)
            txtViewTasker = view.findViewById(R.id.txtViewTasker)
            txtCatDesc = view.findViewById(R.id.txtCatDesc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item_list, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val lstCategory = listCategory[position]
        holder.txtCatTitle.text = lstCategory.categoryName
        holder.txtCatDesc.text = lstCategory.categoryDesc
        val image = ServiceBuilder.loadImagePath() + lstCategory.image
        if (!lstCategory.image.equals("")) {
            Glide.with(context)
                .load(image)
                .into(holder.imgCategory)
        }
        holder.txtViewTasker.setOnClickListener{
            val intent = Intent(context, TaskerRecyclerViewActivity::class.java)
            intent.putExtra("Category", lstCategory.categoryName.toString())
            context.startActivities(arrayOf(intent))
        }

    }

    override fun getItemCount(): Int {
        return listCategory.size
    }
}