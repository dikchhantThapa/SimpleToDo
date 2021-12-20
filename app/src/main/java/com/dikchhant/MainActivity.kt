package com.dikchhant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {


    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter
    // created a variable for the adapter
    // we gave it a lateinit keyboard to tell Kotlin that we are going to initialize it later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        // 1. Let's detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            // Code in here is going to be executed when the user clicks on a button
//            Log.i("Dikchhant", "User clicked on button")
//        }



        loadItems()

        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Set up the button and input field, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Get a reference to the button
        // and then set an onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text the user has inputted into the @+id/addTaskField
            val userInputtedTask = inputTextField.text.toString()
            // we also converted it to a string since it is just an "editable object" currently
            // we called the .toString() method to convert it to a string

            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            /* we insert the data to "listOfTasks.size - 1" since we are adding it to the end of the list
               aka size of the task - 1 (since array starts at zero instead of 1)
             */

            // 3. Reset text field
            inputTextField.setText("")
            // setting text to an empty string

            saveItems()
        }

    }

    // Save the data that the user  has inputted
    // Save data by writing and reading from a file

    // Get the file we need
    fun getDataFile() : File {

        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }   catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    // Save items by writing them into our data file
    fun saveItems() {
        //FileUtils.writeLines(getDataFile(), listOfTasks)
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        }   catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

}