package com.example.servenep

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CategoryAdapter(var context: Context, var arrayList: ArrayList<Category>):BaseAdapter() {
    override fun getCount(): Int {
       return arrayList.size
    }

    override fun getItem(position: Int): Any {
       return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View =View.inflate(context, R.layout.grid_item_list, null)
        var icons:ImageView=view.findViewById(R.id.icons)
        var name:TextView=view.findViewById(R.id.categoryname)
        var category:Category=arrayList.get(position)

        icons.setImageResource(category.icons!!)
        name.text=category.name

        return view
    }
}