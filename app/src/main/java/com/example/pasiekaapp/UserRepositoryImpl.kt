import com.example.pasiekaapp.User
import com.example.pasiekaapp.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
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
}
