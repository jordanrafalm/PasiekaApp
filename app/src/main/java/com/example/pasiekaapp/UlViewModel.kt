package com.example.pasiekaapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class UlViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val ulDocument = db.collection("Ule").document("UL1")

    private val _ulLiveData = MutableLiveData<Ul>()
    val ulLiveData: LiveData<Ul> get() = _ulLiveData

    init {
        loadUlData()
    }

    fun saveUlData(ul: Ul, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        ulDocument.set(ul)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    private fun loadUlData() {
        ulDocument.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val ul = document.toObject(Ul::class.java)
                ul?.let {
                    _ulLiveData.value = it
                }
            }
        }.addOnFailureListener {
        }
    }
}
