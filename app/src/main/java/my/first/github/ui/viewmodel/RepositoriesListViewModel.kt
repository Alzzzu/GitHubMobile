package my.first.github.ui.viewmodel

import androidx.lifecycle.LiveData
import my.first.github.models.Repo

class RepositoriesListViewModel {
//    val state: LiveData<State>

    sealed interface State {
        object Loading : State
        data class Loaded(val repos: List<Repo>) : State
        data class Error(val error: String) : State
        object Empty : State
    }

    // TODO:
}