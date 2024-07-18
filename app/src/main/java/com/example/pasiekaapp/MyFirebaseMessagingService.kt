package com.example.pasiekaapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.firestore.FirebaseFirestore

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "From: ${remoteMessage.from}")

        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        title?.let { t ->
            body?.let { b ->
                showNotification(t, b)
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
        saveTokenToFirestore(token)
    }

     fun saveTokenToFirestore(token: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            val db = FirebaseFirestore.getInstance()
            val userToken = hashMapOf(
                "token" to token
            )
            db.collection("users").document(it.uid)
                .set(userToken)
                .addOnSuccessListener { Log.d("FCM", "Token saved successfully") }
                .addOnFailureListener { e -> Log.w("FCM", "Error saving token", e) }
        } ?: Log.w("FCM", "User not logged in, can't save token")
    }

    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, "all")
            .setSmallIcon(R.drawable.pasiekaapp_logo) // Ikona aplikacji
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Dla Android Oreo (API 26) i wyższych, dodaj kanał powiadomień
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "all",
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, builder.build())
    }
}
