import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasiekaapp.RegisterUseCase
import com.example.pasiekaapp.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _registrationState = MutableStateFlow<Result<User>?>(null)
    val registrationState: StateFlow<Result<User>?> = _registrationState

    fun signUp(fullName: String, emailAddress: String, password: String) {
        viewModelScope.launch {
            registerUseCase.invoke(RegisterUseCase.UserParams(fullName, emailAddress, password))
                .collect { result ->
                    _registrationState.value = result
                }
        }
    }
}
