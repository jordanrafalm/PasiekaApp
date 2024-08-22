package com.example.pasiekaapp

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pasiekaapp.databinding.ActivityHiveinspectionBinding

class HiveInspectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHiveinspectionBinding
    private val ulViewModel: UlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHiveinspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ulViewModel.ulLiveData.observe(this, Observer { ul ->
            ul?.let { updateUI(it) }
        })

        binding.btnNext.setOnClickListener {
            saveData()
        }
    }

    private fun updateUI(ul: Ul) {
        val korpusyIndex = ul.korpusy.toIntOrNull() ?: 0
        val nadstawkiIndex = ul.nadstawki.toIntOrNull() ?: 0

        if (korpusyIndex in 0 until binding.rgKorpusy.childCount) {
            binding.rgKorpusy.check(binding.rgKorpusy.getChildAt(korpusyIndex).id)
        } else {
            binding.rgKorpusy.clearCheck()
        }

        if (nadstawkiIndex in 0 until binding.rgNadstawki.childCount) {
            binding.rgNadstawki.check(binding.rgNadstawki.getChildAt(nadstawkiIndex).id)
        } else {
            binding.rgNadstawki.clearCheck()
        }

        binding.switchPodkarmiaczka.isChecked = ul.podkarmiaczka
        binding.switchPolawiaczPylku.isChecked = ul.polawiaczPylku
        binding.switchIzolator.isChecked = ul.izolator
        binding.switchMata.isChecked = ul.mata
        binding.switchPodgrzewacz.isChecked = ul.podgrzewacz
        binding.switchBalkonik.isChecked = ul.balkonik
    }

    private fun saveData() {
        val korpusyValue = binding.rgKorpusy.checkedRadioButtonId.let { id ->
            findViewById<RadioButton>(id).text.toString().toInt()
        }

        val nadstawkiValue = binding.rgNadstawki.checkedRadioButtonId.let { id ->
            findViewById<RadioButton>(id).text.toString().toInt()
        }

        val ul = Ul(
            korpusy = korpusyValue.toString(),
            nadstawki = nadstawkiValue.toString(),
            podkarmiaczka = binding.switchPodkarmiaczka.isChecked,
            polawiaczPylku = binding.switchPolawiaczPylku.isChecked,
            izolator = binding.switchIzolator.isChecked,
            mata = binding.switchMata.isChecked,
            podgrzewacz = binding.switchPodgrzewacz.isChecked,
            balkonik = binding.switchBalkonik.isChecked
        )

        ulViewModel.saveUlData(ul,
            onSuccess = {
                Toast.makeText(this, "Dane zapisane pomyślnie", Toast.LENGTH_SHORT).show()
            },
            onFailure = { exception ->
                Toast.makeText(this, "Błąd zapisu: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
