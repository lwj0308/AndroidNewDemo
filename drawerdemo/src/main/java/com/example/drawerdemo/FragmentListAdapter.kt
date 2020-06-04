package com.example.drawerdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_cell.view.*

class FragmentListAdapter(private val isPager: Boolean = false) : ListAdapter<Int, RecyclerView.ViewHolder>(callback) {
    object callback : DiffUtil.ItemCallback<Int?>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cell, parent, false)
        if (isPager) {
            view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            view.list_item_icon.layoutParams.height = 0
            view.list_item_icon.layoutParams.width = 0
        }
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.list_item_icon.setImageResource(getItem(position))
    }
}