package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var binding: ActivityAddCourseBinding
    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = AddCourseViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        setupViewModelObserver()
        setupTimePickers()
    }

    private fun setupViewModelObserver() {
        addCourseViewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                finish()
            } else {
                Toast.makeText(this, getString(R.string.input_empty_message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupTimePickers() {
        binding.ibStartTime.setOnClickListener { showTimePicker("startTime") }
        binding.ibEndTime.setOnClickListener { showTimePicker("endTime") }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                insertCourse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertCourse() {
        val courseName = binding.edCourseName.text.toString()
        val day = binding.spinner.selectedItemPosition
        val start = binding.addTvStartTime.text.toString()
        val end = binding.addTvEndTime.text.toString()
        val lecturer = binding.edLecturer.text.toString()
        val notes = binding.edNote.text.toString()

        addCourseViewModel.insertCourse(courseName, day, start, end, lecturer, notes)
    }

    private fun showTimePicker(tag: String) {
        val dialog = TimePickerFragment()
        dialog.show(supportFragmentManager, tag)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time = dateFormat.format(calendar.time)

        if (tag == "startTime") binding.addTvStartTime.text = time
        else if (tag == "endTime") binding.addTvEndTime.text = time
    }
}
