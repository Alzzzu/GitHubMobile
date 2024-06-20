package my.first.github.ui.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import my.first.github.models.UserInfo
import my.first.github.repository.AppRepository
import my.first.github.utils.PreferencesManager
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(var preferencesManager: PreferencesManager) : ViewModel() {
    private val token: MutableLiveData<String> = MutableLiveData()
    val state: MutableLiveData<State> = MutableLiveData()
    @Inject
    lateinit var repository: AppRepository

    fun onSignButtonPressed()  = viewModelScope.launch {
        state.postValue(State.Loading)
        try{
        val response = repository.signIn(token.value.toString())
        state.postValue(handleResponse(response))
        }
        catch(e:Exception){
            state.postValue(State.Error("No connection"))
        }
    }

    fun updateToken(newToken: String){
        token.value = newToken
    }

    fun handleResponse(response: Response<UserInfo>) : State {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return State.Idle
            }
        }
        return State.InvalidInput(response.message())
    }
    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
        data class Error(val error: String) : State
    }
}