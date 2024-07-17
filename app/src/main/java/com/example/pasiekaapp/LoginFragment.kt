package com.example.pasiekaapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.pasiekaapp.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModel()

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


        EmailValidator.attach(binding.emailEditText)
        PasswordValidator.attach(binding.passwordEditText, requireContext())

        viewModel.loginState.observe(viewLifecycleOwner) { loginState ->
            when (loginState) {
                LoginState.SUCCESS -> {
                    val intent = Intent(requireActivity(), DashboardActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                LoginState.ERROR -> {
                    println("Login failed.")
                }

                LoginState.LOADING -> {
                    println("Login is in progress.")
                }
            }
        }
        navigationListeners()
    }

    private fun navigationListeners() {


        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

     /*   binding.forgotPassword.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(false)
            findNavController().navigate(action)
        }



        binding.signUpNow.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }


      */
    }
}