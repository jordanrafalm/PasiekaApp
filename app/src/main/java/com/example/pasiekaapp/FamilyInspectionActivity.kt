package com.example.pasiekaapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pasiekaapp.databinding.ActivityCheckInspectionBinding
import com.example.pasiekaapp.databinding.ActivityFamilyInspectionBinding
import com.example.pasiekaapp.databinding.ActivityHiveinspectionBinding

class FamilyInspectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFamilyInspectionBinding
    private val ulViewModel: UlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityFamilyInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnZakoncz.setOnClickListener {
            finish() }


    }
}