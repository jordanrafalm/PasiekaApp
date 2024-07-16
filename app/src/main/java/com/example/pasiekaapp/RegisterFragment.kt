package com.example.pasiekaapp

import RegisterViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pasiekaapp.databinding.FragmentRegisterBinding
import com.google.android.gms.safetynet.SafetyNet
import org.koin.androidx.viewmodel.ext.android.viewModel
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInputValidation()

        lifecycleScope.launchWhenStarted {
            viewModel.registrationState.collect { result ->
                when {
                    result == null -> {
                        //no-op
                    }

                    result.isSuccess -> {

                    }

                    result.isFailure -> {

                    }
                }
            }
        }
        navigationListeners()
    }

    private fun setupInputValidation() {

        FullNameValidator.attach(binding.nameEditText, requireContext())
        EmailValidator.attach(binding.emailEditText)
        PasswordValidator.attach(binding.passwordEditText, requireContext())
        PasswordValidator.attach(binding.confirmPasswordEditText, requireContext())

        binding.nameEditText.addTextChangedListener {
            binding.registerButton.isEnabled = isValidInput()
        }
        binding.emailEditText.addTextChangedListener {
            binding.registerButton.isEnabled = isValidInput()
        }
        binding.passwordEditText.addTextChangedListener {
            binding.registerButton.isEnabled = isValidInput()
        }
        binding.confirmPasswordEditText.addTextChangedListener {
            binding.registerButton.isEnabled = isValidInput()
        }

        binding.registerButton.isEnabled = false
    }

    private fun isValidInput(): Boolean = binding.nameEditText.isCorrectText() &&
            binding.emailEditText.isCorrectText() &&
            binding.passwordEditText.isCorrectText() &&
            binding.confirmPasswordEditText.isCorrectText() &&
            binding.passwordEditText.text.toString() == binding.confirmPasswordEditText.text.toString()

    private fun navigationListeners() {

        binding.registerButton.setOnClickListener {

            viewModel.signUp(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
            )
        }

    }
}




