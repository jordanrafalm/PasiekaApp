package com.example.pasiekaapp

data class Profile(
    val firebaseId: String,
    val email: String,
    val fcmToken: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val userName: String,

)