package com.example.pasiekaapp

import android.widget.EditText
import androidx.core.util.PatternsCompat
import androidx.core.widget.addTextChangedListener

object EmailValidator {
    fun attach(editText: EditText) {
        editText.addTextChangedListener {
            validateEmail(it.toString(), editText)
        }
    }

    fun validateEmail(email: String, editText: EditText) {
        if (!isValid(email)) {
            editText.error = editText.context.getString(R.string.invalid_email)
        } else {
            editText.error = null
        }
    }

    fun isValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

}