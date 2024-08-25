package com.example.pasiekaapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class PasiekiFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pasieki, container, false)

        val addApiaryButton: Button = view.findViewById(R.id.addApiaryButton)
        addApiaryButton.setOnClickListener {
            val intent = Intent(activity, ApiaryActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}