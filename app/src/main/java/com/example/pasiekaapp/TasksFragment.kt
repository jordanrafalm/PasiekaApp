package com.example.pasiekaapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TasksFragment : Fragment() {

    private lateinit var tasksAdapter: TasksAdapter
    private val tasksList = mutableListOf(
        "wyczyścić miodarkę",
        "usunąć mateczniki",
        "pomalować nowe ule",
        "miodobranie po rzepaku",
        "kontrola czerwiu",
        "miodobranie po gryce",
        "kontrola mateczników"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        val recyclerViewTasks = view.findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerViewTasks.layoutManager = LinearLayoutManager(context)
        tasksAdapter = TasksAdapter(tasksList)
        recyclerViewTasks.adapter = tasksAdapter

        val fabAddTask = view.findViewById<FloatingActionButton>(R.id.fabAddTask)
        fabAddTask.setOnClickListener {
            tasksList.add("Nowe zadanie")
            tasksAdapter.notifyItemInserted(tasksList.size - 1)
        }

        return view
    }
}
