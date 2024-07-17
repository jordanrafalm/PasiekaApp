import android.content.ContentValues
import android.util.Log
import com.example.pasiekaapp.User
import com.example.pasiekaapp.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
) : UserRepository {

    override suspend fun createUser(fullName: String, email: String, password: String): Flow<User> =
        flow {

            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val firebaseUser = authResult.user!!

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build()

            firebaseUser.updateProfile(profileUpdates).await()

            emit(User(firebaseUser.uid, fullName, email))
        }.catch { error ->
            if (error is FirebaseAuthException) {
                throw IllegalStateException(error.message ?: "Error during registration")
            } else {
                throw error
            }
        }
override suspend fun logIn(email: String, password: String): Flow<Result<String>> = callbackFlow {
    val listener = firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firestore = FirebaseFirestore.getInstance()
                val usersCollection = firestore.collection("users")
                val user = firebaseAuth.currentUser
                val userUUID = user?.uid.orEmpty()
              //  changeSession()
                trySend(Result.success(userUUID))

                val docRef = usersCollection.document(userUUID)
                val userDocumentReference = firestore.collection("users").document(userUUID)

                docRef.get().addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
                        val firebaseId = document.getString("FirebaseId").orEmpty()
                        if (firebaseId == userUUID) {
                            Log.d(ContentValues.TAG, "Profile exists with FirebaseId: $firebaseId")
                        } else {
                            Log.d(ContentValues.TAG, "No matching FirebaseId found")
                        }
                    } else {
                        userDocumentReference.get().addOnSuccessListener { document ->
                            if (document != null) {
                                val firebaseId = document.getString("FirebaseId").orEmpty()
                                if (firebaseId.isEmpty()) {
                                    val userData = hashMapOf(
                                        "FirebaseId" to userUUID,
                                        // można tu inicjalizować pozostałe pola profilu
                                    )
                                    userDocumentReference.set(userData).addOnSuccessListener {
                                        Log.d("Firestore", "DocumentSnapshot successfully written!")
                                    }
                                        .addOnFailureListener { e ->
                                            Log.w("Firestore", "Error writing document", e)
                                        }
                                }
                            } else {
                                Log.i("FirebaseConfigRepository", "Authentication failed.")
                            }
                        }
                    }
                }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                    }
            } else
                trySend(Result.failure(task.exception ?: Exception("failed")))
        }
    awaitClose { listener.isCanceled }
    }
}

