package com.jio.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jio.tesseract.model.PhoneData
import kotlinx.android.synthetic.main.app_level_item.view.*


class PhoneDataAdapter(var context: Context, private var searchResults: List<PhoneData>) : RecyclerView.Adapter<PhoneDataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.app_level_item,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount() = searchResults.size
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
                .load(searchResults.get(position).icon)
                .into(holder.itemView.image_name);
        holder.itemView.app_name.text = searchResults.get(position).appName
        holder.itemView.package_name.text = searchResults.get(position).packageName
        holder.itemView.class_name.text = searchResults.get(position).className
        holder.itemView.version.text = searchResults.get(position).name + " | " + searchResults.get(
                position
        ).code
        holder.itemView.setOnClickListener {
            val pm = context.packageManager
            val launchIntent = searchResults.get(position).packageName?.let { it1 ->
                pm.getLaunchIntentForPackage(
                        it1
                )
            }
            context.startActivity(launchIntent)
        }

    }

    fun setFilter(FilteredDataList: List<PhoneData>) {
        searchResults = FilteredDataList
        notifyDataSetChanged()
    }
}