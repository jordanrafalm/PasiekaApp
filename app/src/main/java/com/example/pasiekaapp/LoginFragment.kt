package com.example.pasiekaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.pasiekaapp.R
import com.example.pasiekaapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInputValidation()
        }

    private fun setupInputValidation() {

        EmailValidator.attach(binding.emailEditText)
        PasswordValidator.attach(binding.passwordEditText, requireContext())

        binding.passwordEditText.addTextChangedListener {
            binding.loginButton.isEnabled = isValidInput()
        }

        binding.emailEditText.addTextChangedListener {
            binding.loginButton.isEnabled = isValidInput()
        }

        binding.loginButton.isEnabled = false

    }
    private fun isValidInput(): Boolean {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        return email.isNotEmpty() && password.isNotEmpty()
    }
}

