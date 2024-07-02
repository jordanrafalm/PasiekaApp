package com.example.pasiekaapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pasiekaapp.ui.LoginFragment
import com.example.pasiekaapp.ui.RegisterFragment
import com.example.pasiekaapp.ui.SplashScreenFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)
        val splashButton: Button = findViewById(R.id.splashButton)

        loginButton.setOnClickListener {
            openFragment(LoginFragment())
        }

        registerButton.setOnClickListener {
            openFragment(RegisterFragment())
        }

        splashButton.setOnClickListener {
            openFragment(SplashScreenFragment())
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
