package com.miempresa.menudown

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class RecipeAdapter(private val context: Context,
                    private val dataSource: ArrayList<Elementos>): BaseAdapter() {
    override fun getCount(): Int {
        TODO("Not yet implemented")
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }
}