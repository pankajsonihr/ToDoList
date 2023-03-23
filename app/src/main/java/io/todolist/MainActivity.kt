package io.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import io.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Define an array to hold the tasks
        val tasks = mutableListOf<String>()

        // Create an ArrayAdapter to display the tasks in the ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, tasks)

        // Set the adapter and choice mode for the ListView
        binding.listViewTasks.adapter = adapter
        binding.listViewTasks.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        // Set the click listener for the Add Task button
        binding.buttonAddTask.setOnClickListener {
            val task = binding.editTextTask.text.toString()
            if (task.isNotEmpty()) {
                tasks.add(task)
                adapter.notifyDataSetChanged()
                binding.editTextTask.text.clear()
            }
        }

        // Set the click listener for the Remove Checked Tasks button
        binding.buttonRemoveCheckedTasks.setOnClickListener {
            val checkedPositions = binding.listViewTasks.checkedItemPositions
            val checkedIndices = mutableListOf<Int>()
            for (i in 0 until checkedPositions.size()) {
                if (checkedPositions.valueAt(i)) {
                    checkedIndices.add(checkedPositions.keyAt(i))
                }
            }
            for (i in checkedIndices.size - 1 downTo 0) {
                tasks.removeAt(checkedIndices[i])
            }
            binding.listViewTasks.clearChoices()
            adapter.notifyDataSetChanged()
        }

    }

}