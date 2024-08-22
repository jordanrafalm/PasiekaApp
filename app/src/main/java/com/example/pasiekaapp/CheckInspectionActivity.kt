package com.example.pasiekaapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pasiekaapp.databinding.ActivityCheckInspectionBinding

class CheckInspectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckInspectionBinding
    private val ulViewModel: UlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityCheckInspectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ulViewModel.ulLiveData.observe(this, Observer { ul ->
            ul?.let { updateUI(it) }
        })

        binding.btnFinish.setOnClickListener {
            finish() }

    }

    private fun updateUI(ul: Ul) {
        // Funkcja pomocnicza do ustawiania tekstu dla switcha
        fun setSwitchText(switch: TextView, value: Boolean) {
            switch.text = if (value) "Występuje" else "Brak"
        }


        setSwitchText(binding.switchPodkarmiaczka, ul.podkarmiaczka)
        setSwitchText(binding.switchPolawiaczPylku, ul.polawiaczPylku)
        setSwitchText(binding.switchIzolator, ul.izolator)
        setSwitchText(binding.switchMata, ul.mata)
        setSwitchText(binding.switchPodgrzewacz, ul.podgrzewacz)
        setSwitchText(binding.switchBalkonik, ul.balkonik)

        binding.rgKorpusy.text = "Ilość korpusów: ${ul.korpusy}"
        binding.rgNadstawki.text = "Ilość nadstawek: ${ul.nadstawki}"
    }

}

