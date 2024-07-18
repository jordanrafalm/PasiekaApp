package com.example.pasiekaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pasiekaapp.databinding.FragmentLaunchScreenBinding

class LaunchScreenFragment : Fragment() {

    private lateinit var binding: FragmentLaunchScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationListeners()
    }


    private fun navigationListeners() {
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_launchScreenFragment_to_loginFragment)

        }

    }
}

