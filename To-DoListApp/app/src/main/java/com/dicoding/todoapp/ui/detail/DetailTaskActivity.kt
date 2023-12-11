package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)


        val taskId = intent.getIntExtra(TASK_ID, 0)
        if (taskId != 0) {
            viewModel.setTaskId(taskId)
        }

        setupObservers()
        setupDeleteAction()
    }

    private fun setupObservers() {
        viewModel.task.observe(this) { task ->
            task?.let { displayTaskDetails(it) }
        }
    }

    private fun displayTaskDetails(task: Task) {
        findViewById<TextInputEditText>(R.id.detail_ed_title).setText(task.title)
        findViewById<TextInputEditText>(R.id.detail_ed_description).setText(task.description)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        task.dueDateMillis.let {
            findViewById<TextInputEditText>(R.id.detail_ed_due_date).setText(dateFormat.format(Date(it)))
        }
    }


    private fun setupDeleteAction() {
        val deleteButton = findViewById<Button>(R.id.btn_delete_task)
        deleteButton.setOnClickListener {
            viewModel.deleteTask()
            finish()
        }
    }
}