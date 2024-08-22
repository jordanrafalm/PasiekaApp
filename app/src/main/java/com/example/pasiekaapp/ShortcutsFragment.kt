package com.example.pasiekaapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton


class ShortcutsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shortcuts, container, false)

        val qrButton: ImageButton = view.findViewById(R.id.qr_button)
        qrButton.setOnClickListener {
            val intent = Intent(activity, QrScanActivity::class.java)
            startActivity(intent)
        }

        val nfcButton: ImageButton = view.findViewById(R.id.nfc_button)
        nfcButton.setOnClickListener {
            val intent = Intent(activity, NFCActivity::class.java)
            startActivity(intent)
        }

        val addInspectionButton: Button = view.findViewById(R.id.add_inspection_button)
        addInspectionButton.setOnClickListener {
            val intent = Intent(activity, HiveInspectionActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}