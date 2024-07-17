package com.example.pasiekaapp

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.provider.Settings

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var buttonContainer: LinearLayout
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        setupFirebase()

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            splashScreenViewModel.delay.isActive
        }

        if (!areNotificationsEnabled()) {
            showNotificationSettingsDialog()
        }



        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.d("FCM", "Token: $token")

            // Save the token to Firestore
            val myService = MyFirebaseMessagingService()
            myService.saveTokenToFirestore(token)
        }

        buttonContainer = findViewById(R.id.button_container)

        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)
        val splashButton: Button = findViewById(R.id.splashButton)
        val dashboardButton: Button = findViewById(R.id.dashboardButton)
        val tasksButton: Button = findViewById(R.id.taskButton)
        val optionsButton: Button = findViewById(R.id.optionsButton)

        loginButton.setOnClickListener { openFragment(LoginFragment()) }
        registerButton.setOnClickListener { openFragment(RegisterFragment()) }
        splashButton.setOnClickListener { openFragment(SplashScreenFragment()) }
        tasksButton.setOnClickListener { openFragment(TasksFragment()) }
        optionsButton.setOnClickListener { openFragment(OptionsFragment()) }
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

    private fun registerUser(email: String, password: String, fullName: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = task.result?.user

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build()

                    firebaseUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                println("User profile updated: ${firebaseUser.displayName}")
                            }
                        }
                } else {
                    println("User creation failed: ${task.exception?.message}")
                }
            }
    }

    private fun setupFirebase() {
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
    }

    private fun areNotificationsEnabled(): Boolean {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }

    private fun showNotificationSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Włącz powiadomienia")
            .setMessage("Aby korzystać z wszystkich funkcji aplikacji pszczelarskiej, włącz powiadomienia.")
            .setPositiveButton("Ustawienia powiadomień") { _, _ ->
                openNotificationSettings()
            }
            .show()
    }

    private fun openNotificationSettings() {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
        } else {
            Intent("android.settings.APP_NOTIFICATION_SETTINGS").apply {
                putExtra("app_package", packageName)
                putExtra("app_uid", applicationInfo.uid)
            }
        }
        startActivity(intent)
    }



    companion object {
        private const val TAG = "MainActivity"
    }
}
