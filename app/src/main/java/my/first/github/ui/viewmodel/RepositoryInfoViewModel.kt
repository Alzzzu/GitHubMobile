package my.first.github.ui.viewmodel

import androidx.lifecycle.LiveData
import my.first.github.models.Repo

class RepositoryInfoViewModel {
//    val state: LiveData<State>


    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State

        data class Loaded(
            val githubRepo: Repo,
            val readmeState: ReadmeState
        ) : State
    }

    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState
        data class Error(val error: String) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }

    // TODO:
}