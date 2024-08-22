package com.example.pasiekaapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton


class PasiekaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pasieka, container, false)

        val addInspectionButton: Button = view.findViewById(R.id.add_inspection_button)
        addInspectionButton.setOnClickListener {
            val intent = Intent(activity, HiveInspectionActivity::class.java)
            startActivity(intent)
        }

        val checkInspectionButton: Button = view.findViewById(R.id.check_inspection_button)
        checkInspectionButton.setOnClickListener {
            val intent = Intent(activity, CheckInspectionActivity::class.java)
            startActivity(intent)
        }

        val addTaskButton: Button = view.findViewById(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            navigateToTasksFragment()
        }

        return view
    }

    private fun navigateToTasksFragment() {
        val tasksFragment = TasksFragment()

        // Ustawienie flagi, aby wiedzieć, że trzeba wywołać showAddTaskDialog
        tasksFragment.arguments = Bundle().apply {
            putBoolean("showAddTaskDialog", true)
        }

        // Nawigacja do TasksFragment
        parentFragmentManager.beginTransaction()
            .replace(R.id.quick_actions_container, tasksFragment) // Upewnij się, że fragment_container jest prawidłowym ID layoutu, w którym umieszczasz fragmenty
            .addToBackStack(null) // Dodaj do backstack, aby umożliwić nawigację wstecz
            .commit()
    }
}