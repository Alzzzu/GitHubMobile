package my.first.github.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

class AuthViewModel() {
/*    val token: MutableLiveData<String>
    val state: LiveData<State>
    val actions: Flow<Action>


 */
    fun onSignButtonPressed() {
        // TODO:
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }
}