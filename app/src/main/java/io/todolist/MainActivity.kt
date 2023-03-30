package io.todolist

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import io.todolist.databinding.ActivityMainBinding
import java.util.*
//Group Application Pankaj, Mahroon Nisha, Siva Sai
class MainActivity : AppCompatActivity() {
lateinit var binding: ActivityMainBinding
    private var selectedTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using the binding object and set it as the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Define an array to hold the tasks
        val tasks = mutableListOf<String>()

        // Create an ArrayAdapter to display the tasks in the ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, tasks)

        // Set the adapter and choice mode for the ListView
        binding.listViewTasks.adapter = adapter
        binding.listViewTasks.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        // Set the click listener for the Set Time button
        binding.buttonSetTime.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            // Create a TimePickerDialog to allow the user to select a time
            val dialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    val time = "$hourOfDay:$minute"
                    selectedTime = time
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false
            )
            dialog.show()
        }

        // Set the click listener for the Add Task button
        binding.buttonAddTask.setOnClickListener {
            // Get the task text from the EditText
            val task = binding.editTextTask.text.toString()
            // Check if the task text is not empty
            if (task.isNotEmpty()) {
                val timeString = selectedTime ?: ""
                val taskWithTime = if (timeString.isEmpty()) {
                    task
                } else {
                    String.format("%s (%s)", task, timeString)
                }
                // Add the task to the tasks list, notify the adapter of the change, clear the EditText, and reset the selected time
                tasks.add(taskWithTime)
                adapter.notifyDataSetChanged()
                binding.editTextTask.text.clear()
                selectedTime = null
            }
        }


        // Set the click listener for the Remove Checked Tasks button
        binding.buttonRemoveCheckedTasks.setOnClickListener {
            // Get the positions of the checked items in the ListView
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
            // Remove the checked tasks from the tasks list, clear the checked items in the ListView, and notify the adapter of the change
            binding.listViewTasks.clearChoices()
            adapter.notifyDataSetChanged()
        }

    }

}