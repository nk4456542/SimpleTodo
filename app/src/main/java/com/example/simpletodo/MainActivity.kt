package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClickListener(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        loadItems()

        // Recycler View
        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        rvTasks.adapter = adapter
        rvTasks.layoutManager = LinearLayoutManager(this)

        val userEnteredTaskEditText = findViewById<EditText>(R.id.etEditTask)
        val addButton = findViewById<Button>(R.id.btnAddTask)
        addButton.setOnClickListener {
            val userEnteredTask = userEnteredTaskEditText.text.toString()
            if(userEnteredTask.isNotEmpty()){
                listOfTasks.add(userEnteredTask)
                adapter.notifyItemInserted(listOfTasks.size - 1)
            }
            userEnteredTaskEditText.setText("")

            saveItems()
        }
    }

    // Persist Data to a file

    fun getDataFile(): File {
        return File(filesDir,"taskList.txt")
    }

    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

}