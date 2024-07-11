package com.example.pasiekaapp

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var buttonContainer: LinearLayout
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            splashScreenViewModel.delay.isActive
        }



        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

        buttonContainer = findViewById(R.id.button_container)

        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)
        val splashButton: Button = findViewById(R.id.splashButton)
        val dashboardButton: Button = findViewById(R.id.dashboardButton)
        val tasksButton: Button = findViewById(R.id.taskButton)
        val optionsButton: Button = findViewById(R.id.optionsButton)

        loginButton.setOnClickListener {
            openFragment(LoginFragment())
        }

        registerButton.setOnClickListener {
            openFragment(RegisterFragment())
        }

        splashButton.setOnClickListener {
            openFragment(SplashScreenFragment())
        }

        tasksButton.setOnClickListener {
            openFragment(TasksFragment())
        }

        optionsButton.setOnClickListener {
            openFragment(OptionsFragment())
        }

        dashboardButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openFragment(fragment: Fragment) {
        buttonContainer.visibility = LinearLayout.GONE
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            buttonContainer.visibility = LinearLayout.VISIBLE
        } else {
            super.onBackPressed()
        }
    }
}
