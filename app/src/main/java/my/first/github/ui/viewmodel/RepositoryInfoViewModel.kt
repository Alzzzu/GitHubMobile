package my.first.github.ui.viewmodel

import android.app.Application
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.first.github.models.Readme
import my.first.github.models.RepoItem
import my.first.github.repository.AppRepository
import my.first.github.utils.PreferencesManager
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RepositoryInfoViewModel @Inject constructor(
    var preferencesManager: PreferencesManager
) : ViewModel() {
    @Inject
    lateinit var repository: AppRepository
    val state: MutableLiveData<ReadmeState> = MutableLiveData()

    fun getReadme(repoItem: RepoItem) = viewModelScope.launch {
        state.postValue(ReadmeState.Loading)
        try {
            val response = repository.getRepositoryReadme(

                repoItem.owner.login, repoItem.name
            )
            state.postValue(handleResponse(response))
        }
        catch (e:Exception){
            state.postValue(ReadmeState.Error("no connection"))
        }
    }

    private fun handleResponse(response: Response<Readme>):ReadmeState{
        Log.d("PUPURA", response.raw().toString())
        if (response.isSuccessful){
            (response.body() as Readme).apply{
                return if (this.content.isEmpty()){
                    ReadmeState.Empty
                } else{
                    ReadmeState.Loaded(Base64.decode(this.content, 0).decodeToString())
                }
            }
        }

        return ReadmeState.Empty

    }


    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState
        data class Error(val error: String) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }
}