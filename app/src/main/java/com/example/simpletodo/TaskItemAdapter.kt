package com.example.simpletodo

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A bridge that tells the recycler view how to display the data we give it.
 */

class TaskItemAdapter(private val listOfItems: List<String>, val longClickListener: OnLongClickListener): RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun onItemLongClickListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfItems[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        // Store references to elements in our layout item
        val textView: TextView
        init {
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnLongClickListener {
//                Log.i("message","Long Click on Task " + adapterPosition)
                longClickListener.onItemLongClickListener(adapterPosition)
                true
            }
        }
    }
}