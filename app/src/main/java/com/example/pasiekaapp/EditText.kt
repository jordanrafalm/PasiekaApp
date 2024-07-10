package com.example.pasiekaapp

import android.widget.EditText
internal fun EditText.isCorrectText(): Boolean {
    return if (this.text.isNullOrEmpty()) {
        false
    } else {
        this.error.isNullOrEmpty()
    }
}
