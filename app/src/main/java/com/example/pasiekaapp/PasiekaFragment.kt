package com.example.pasiekaapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class PasiekaFragment : Fragment() {

    private val apiaryViewModel: ApiaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pasieka, container, false)

        val apiaryNameTextView: TextView = view.findViewById(R.id.quick_actions_title)
        val apiaryStatusTextView: TextView = view.findViewById(R.id.stan_pasieki)

        // Pobierz nazwę dokumentu z argumentów
        val documentName = arguments?.getString("documentName") ?: "PASIEKA1"

        // Observe the LiveData from the ViewModel
        apiaryViewModel.apiaryLiveData.observe(viewLifecycleOwner, Observer { apiary ->
            apiary?.let {
                // Update UI with the data
                apiaryNameTextView.text = it.name
                apiaryStatusTextView.text = "Stan:\n• ${it.totalHives} wszystkich uli\n• ${it.strongHives} rodzin silnych\n• ${it.noBroodHives} rodzin bez czerwiu"
            }
        })

        // Naładuj dane dla konkretnej pasieki
        apiaryViewModel.loadApiaryData(documentName)

        val addInspectionButton: Button = view.findViewById(R.id.add_inspection_button)
        addInspectionButton.setOnClickListener {
            // Przejście do aktywności przeglądu ula
            val intent = Intent(activity, HiveInspectionActivity::class.java)
            startActivity(intent)
        }

        val checkInspectionButton: Button = view.findViewById(R.id.check_inspection_button)
        checkInspectionButton.setOnClickListener {
            // Przejście do aktywności sprawdzania przeglądu
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
            .replace(R.id.quick_actions_container, tasksFragment)
            .addToBackStack(null)
            .commit()
    }
}
