package my.first.github.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.first.github.models.RepoItem
import my.first.github.repository.AppRepository
import my.first.github.utils.PreferencesManager
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RepositoriesListViewModel @Inject constructor(
    var preferencesManager: PreferencesManager
) : ViewModel() {
    @Inject
    lateinit var repository: AppRepository
    val state: MutableLiveData<State> = MutableLiveData()


    fun getRepos() = viewModelScope.launch {
        state.postValue(State.Loading)
        try {
            val response = repository.getRepositories()
            state.postValue(handleResponse(response))
        }
        catch (e:Exception) {
            state.postValue(State.Error("no connection"))
        }
    }

    private fun handleResponse(response: Response<List<RepoItem>>) : State{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return  if (resultResponse.isEmpty()) State.Empty else State.Loaded(resultResponse)
            }
        }
        return State.Error(response.message())
    }


    sealed interface State {
        object Loading : State
        data class Loaded(val repos: List<RepoItem>) : State
        data class Error(val error: String) : State
        object Empty : State
    }
}