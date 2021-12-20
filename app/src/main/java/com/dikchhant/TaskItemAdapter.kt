package com.dikchhant


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
    * A bridge that tells the recyclerView how to display the data we give it
 */

class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener: OnLongClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
        // position is to point out the exact item that is long clicked
    }


    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = listOfItems.get(position)
        // getting the item inside our list of items

        holder.textView.text = item
        // setting the textView to whatever the string "item" is

    }

    override fun getItemCount(): Int {
        return listOfItems.size
        // returns how many elements this recycleView should be expected to layout
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        // Store references to elements in our layout view
        val textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
            longClickListener.onItemLongClicked(adapterPosition)
                /* once an item is LongClicked, it's going to look at the longClickListener
                that was passed in and call the onItemLongClicked method along with adapterPosition,
                which was the item that was specifically
                 */
                true
            }
        }
    }
}
