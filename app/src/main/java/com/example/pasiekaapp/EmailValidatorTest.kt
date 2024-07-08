package com.example.pasiekaapp

import android.content.Context
import android.widget.EditText
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EmailValidatorTest {

    companion object {
        private const val ERROR_TEXT = "Invalid email"
    }

    @RelaxedMockK
    lateinit var editText: EditText

    @RelaxedMockK
    lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { editText.context } returns context
        every { context.getString(R.string.invalid_email) } returns ERROR_TEXT
    }

    @Test
    fun attachAddsTextChangedListenerToEditText() {
        EmailValidator.attach(editText)
        verify { editText.addTextChangedListener(any()) }
    }

    @Test
    fun validateEmailSetsErrorMessageWhenEmailIsInvalid() {
        val invalidEmail = "drewno@"
        EmailValidator.validateEmail(invalidEmail, editText)
        verify { editText.error = ERROR_TEXT }
    }

    @Test
    fun validateEmailClearsErrorWhenEmailIsValid() {
        val validEmail = "drewno@gmail.com"
        val mockEditText = mockk<EditText>(relaxed = true)
        EmailValidator.validateEmail(validEmail, mockEditText)
        verify { mockEditText.error = null }
    }

    @Test
    fun isValidReturnsTrueForValidEmail() {
        val validEmail = "drewno@gmail.com"
        assertTrue(EmailValidator.isValid(validEmail))
    }

    @Test
    fun isValidReturnsFalseWhenEmailIsInvalid() {
        val invalidEmail = "drewno@"
        assertFalse(EmailValidator.isValid(invalidEmail))
    }

    @Test
    fun isValidReturnsTrueForEmailWithDotInAddress() {
        val validEmail = "drewno.drewno@gmail.com"
        assertTrue(EmailValidator.isValid(validEmail))
    }

    @Test
    fun isValidReturnsTrueForEmailWithPlusInAddress() {
        val validEmail = "drewno+drewno@gmail.com"
        assertTrue(EmailValidator.isValid(validEmail))
    }

    @Test
    fun isValidReturnsTrueForEmailWithDigitsInAddress() {
        val validEmail = "drewno1234@gmail.com"
        assertTrue(EmailValidator.isValid(validEmail))
    }

    @Test
    fun isValidReturnsFalseIfAtSymbolIsMissing() {
        val invalidEmail = "drewnogmail.com"
        assertFalse(EmailValidator.isValid(invalidEmail))
    }

    @Test
    fun isValidReturnsFalseIfDotSymbolAppearsAtTheEndOfEmail() {
        val invalidEmail = "drewno@gmail..com"
        assertFalse(EmailValidator.isValid(invalidEmail))
    }

    @Test
    fun isValidReturnsFalseIfUsernameIsMissing() {
        val invalidEmail = "@gmail.com"
        assertFalse(EmailValidator.isValid(invalidEmail))
    }
}