package com.example.task71

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val setOnItemClickListener: SetOnItemClickListener) : RecyclerView.Adapter<Adapter.LostAndFoundViewHolder>() {

    private var list = ArrayList<LostAndFound>()

    // Method to submit a new list of data to the adapter
    fun submit(list: ArrayList<LostAndFound>) {
        this.list = list
        notifyDataSetChanged()
    }

    // ViewHolder class for holding the views of each item in the RecyclerView
    class LostAndFoundViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val heading: TextView = view.findViewById(R.id.heading)
        private val detail: TextView = view.findViewById(R.id.detail)
        // Bind the data to the views of the ViewHolder
        fun bind(
            lostAndFound: LostAndFound,
            setOnItemClickListener: SetOnItemClickListener
        ) {
            // Set the text for the heading and detail views
            heading.text = "${lostAndFound.isLostOrFound}: ${lostAndFound.name}"
            detail.text = StringBuilder().apply {
                append(lostAndFound.date)
                append("\n")
                append(lostAndFound.location)
                append("\n")
                append(lostAndFound.phone)
                append("\n")
                append(lostAndFound.description)
            }.toString()

            // Set click listener for the item view
            view.setOnClickListener {
                setOnItemClickListener.onItemClickListener(lostAndFound)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LostAndFoundViewHolder {
        // Inflate the view for each item in the RecyclerView
        return LostAndFoundViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view,parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LostAndFoundViewHolder, position: Int) {
        // Bind the data to the ViewHolder
        holder.bind(lostAndFound = list[position],setOnItemClickListener)
    }
}
// Interface for handling item click events in the RecyclerView
interface SetOnItemClickListener {
    fun onItemClickListener(lostAndFound: LostAndFound)
}