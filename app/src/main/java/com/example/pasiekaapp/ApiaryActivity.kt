package com.example.pasiekaapp

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pasiekaapp.databinding.ActivityApiaryBinding
import androidx.lifecycle.Observer

class ApiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiaryBinding
    private val apiaryViewModel: ApiaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiaryViewModel.apiaryLiveData.observe(this, Observer { apiary ->
            apiary?.let { updateUI(it) }
        })

        binding.btnAdd.setOnClickListener {
            saveData()

            // Można zaimplementować dalszą nawigację lub działania po dodaniu danych
            Toast.makeText(this, "Pasieka dodana pomyślnie", Toast.LENGTH_SHORT).show()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }
    }

    private fun updateUI(apiary: Apiary) {
        val totalHivesIndex = apiary.totalHives.toIntOrNull() ?: 0
        val strongHivesIndex = apiary.strongHives.toIntOrNull() ?: 0
        val noBroodHivesIndex = apiary.noBroodHives.toIntOrNull() ?: 0

        if (totalHivesIndex in 0 until binding.ule.childCount) {
            binding.ule.check(binding.ule.getChildAt(totalHivesIndex).id)
        } else {
            binding.ule.clearCheck()
        }

        if (strongHivesIndex in 0 until binding.uleSilne.childCount) {
            binding.uleSilne.check(binding.uleSilne.getChildAt(strongHivesIndex).id)
        } else {
            binding.uleSilne.clearCheck()
        }

        if (noBroodHivesIndex in 0 until binding.uleBezCzerwiu.childCount) {
            binding.uleBezCzerwiu.check(binding.uleBezCzerwiu.getChildAt(noBroodHivesIndex).id)
        } else {
            binding.uleBezCzerwiu.clearCheck()
        }
    }

    private fun saveData() {
        val totalHivesValue = binding.ule.checkedRadioButtonId.let { id ->
            if (id != -1) {
                findViewById<RadioButton>(id).text.toString().toInt()
            } else {
                0 // Domyślna wartość, jeśli nic nie zostało wybrane
            }
        }

        val strongHivesValue = binding.uleSilne.checkedRadioButtonId.let { id ->
            if (id != -1) {
                findViewById<RadioButton>(id).text.toString().toInt()
            } else {
                0 // Domyślna wartość, jeśli nic nie zostało wybrane
            }
        }

        val noBroodHivesValue = binding.uleBezCzerwiu.checkedRadioButtonId.let { id ->
            if (id != -1) {
                findViewById<RadioButton>(id).text.toString().toInt()
            } else {
                0 // Domyślna wartość, jeśli nic nie zostało wybrane
            }
        }

        val apiary = Apiary(
            name = binding.editTextNazwaPasieki.text.toString(),
            totalHives = totalHivesValue.toString(),
            strongHives = strongHivesValue.toString(),
            noBroodHives = noBroodHivesValue.toString()
        )

        apiaryViewModel.saveApiaryData(apiary,
            onSuccess = {
                Toast.makeText(this, "Dane pasieki zapisane pomyślnie", Toast.LENGTH_SHORT).show()
            },
            onFailure = { exception ->
                Toast.makeText(this, "Błąd zapisu: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
