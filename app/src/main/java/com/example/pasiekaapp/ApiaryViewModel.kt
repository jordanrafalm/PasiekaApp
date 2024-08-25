package com.example.pasiekaapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ApiaryViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val apiariesCollection = db.collection("Pasieki")

    private val _apiaryLiveData = MutableLiveData<Apiary>()
    val apiaryLiveData: LiveData<Apiary> get() = _apiaryLiveData

    // LiveData for counting the number of apiaries
    private val _apiaryCountLiveData = MutableLiveData<Int>()
    val apiaryCountLiveData: LiveData<Int> get() = _apiaryCountLiveData


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

        }
    }


    fun loadApiaryCount() {
        apiariesCollection.get().addOnSuccessListener { documents ->
            _apiaryCountLiveData.value = documents.size()
        }.addOnFailureListener { exception ->
            _apiaryCountLiveData.value = 0
        }
    }
}
