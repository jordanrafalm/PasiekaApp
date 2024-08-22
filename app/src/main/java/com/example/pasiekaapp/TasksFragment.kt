package com.example.pasiekaapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TasksFragment : Fragment() {

    private lateinit var tasksAdapter: TasksAdapter
    private val tasksList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        loadTasks()  // Wczytaj zadania z SharedPreferences

        tasksAdapter = TasksAdapter(tasksList) {
            saveTasks()  // Przekaż funkcję do zapisania zadań po usunięciu
        }

        val recyclerViewTasks = view.findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerViewTasks.layoutManager = LinearLayoutManager(context)
        recyclerViewTasks.adapter = tasksAdapter

        val fabAddTask = view.findViewById<FloatingActionButton>(R.id.fabAddTask)
        fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }

        arguments?.getBoolean("showAddTaskDialog")?.let { shouldShowDialog ->
            if (shouldShowDialog) {
                showAddTaskDialog()
                arguments?.remove("showAddTaskDialog")
            }
        }
        return view
    }

    private fun showAddTaskDialog() {
        val editText = EditText(requireContext()).apply {
            hint = "Wpisz nazwę zadania"
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Dodaj nowe zadanie")
            .setView(editText)
            .setPositiveButton("Dodaj") { _, _ ->
                val taskName = editText.text.toString()
                if (taskName.isNotBlank()) {
                    tasksList.add(taskName)
                    tasksAdapter.notifyItemInserted(tasksList.size - 1)
                    saveTasks()  // Zapisz zaktualizowaną listę zadań
                    setDailyReminder(taskName)
                }
            }
            .setNegativeButton("Anuluj", null)
            .create()

        dialog.show()

        editText.requestFocus()
        dialog.window?.setSoftInputMode(
            android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

    private fun setDailyReminder(taskName: String) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), TaskReminderReceiver::class.java).apply {
            putExtra("task_name", taskName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            taskName.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = System.currentTimeMillis() + 5000  // 5 sekund od teraz w ramach testów
        val intervalTime = AlarmManager.INTERVAL_DAY

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            intervalTime,
            pendingIntent
        )

        Toast.makeText(
            requireContext(),
            "Codzienne przypomnienie ustawione dla: $taskName",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun saveTasks() {
        val sharedPreferences = requireContext().getSharedPreferences("tasks_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val tasksJson = Gson().toJson(tasksList)
        editor.putString("tasks_list", tasksJson)
        editor.apply()
    }

    private fun loadTasks() {
        val sharedPreferences = requireContext().getSharedPreferences("tasks_prefs", Context.MODE_PRIVATE)
        val tasksJson = sharedPreferences.getString("tasks_list", null)
        if (!tasksJson.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<String>>() {}.type
            tasksList.clear()
            tasksList.addAll(Gson().fromJson(tasksJson, type))
        }
    }
}
