package com.example.pasiekaapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pasiekaapp.Apiary
import com.google.firebase.firestore.FirebaseFirestore

class ApiaryViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val apiariesCollection = db.collection("Pasieki")

    private val _apiaryLiveData = MutableLiveData<Apiary>()
    val apiaryLiveData: LiveData<Apiary> get() = _apiaryLiveData



    fun saveApiaryData(apiary: Apiary, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        apiariesCollection.get().addOnSuccessListener { documents ->
            val nextNumber = documents.size() + 1
            val newDocumentName = "PASIEKA$nextNumber"
            val newDocument = apiariesCollection.document(newDocumentName)

            newDocument.set(apiary)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    // Opcjonalnie metoda do pobierania danych jednej pasieki, jeśli potrzebne
    fun loadApiaryData(documentName: String) {
        val apiaryDocument = apiariesCollection.document(documentName)
        apiaryDocument.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val apiary = document.toObject(Apiary::class.java)
                apiary?.let {
                    _apiaryLiveData.value = it
                }
            }
        }.addOnFailureListener {
            // Możesz dodać obsługę błędów tutaj
        }
    }
}
