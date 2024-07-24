package com.example.pasiekaapp


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pasiekaapp.R
import com.google.android.material.snackbar.Snackbar
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.google.zxing.ResultPoint

class QrScanActivity : AppCompatActivity() {

    private lateinit var barcodeView: DecoratedBarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scan)

        barcodeView = findViewById(R.id.barcode_scanner)

        // Sprawdź uprawnienia do kamery
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            startScanning()
        }
    }

    private fun startScanning() {
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                handleQRCode(result.text)
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })
    }

    private fun handleQRCode(contents: String) {
        when (contents) {
            "1" -> showSnackbar("Dodaj przegląd do ULA nr 1")
            "2" -> showSnackbar("Dodaj przegląd do ULA nr 2")
            else -> showSnackbar("Nieznany kod QR")
        }
    }

    private fun showSnackbar(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }
}
