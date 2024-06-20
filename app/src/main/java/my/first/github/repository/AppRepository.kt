package my.first.github.repository


import my.first.github.retrofit.RetrofitInstance
import my.first.github.utils.Constants
import my.first.github.utils.PreferencesManager
import javax.inject.Inject

class AppRepository @Inject constructor(private val preferencesManager: PreferencesManager) {

    suspend fun getRepositories() =
         RetrofitInstance.api.getReposList(
             "token ${preferencesManager.getString(Constants.KEY_AUTH_TOKEN)}"
         )

    suspend fun getRepositoryReadme(ownerName: String, repositoryName: String) =
        RetrofitInstance.api.getRepositoryReadme(ownerName, repositoryName)

    suspend fun signIn(token: String) = RetrofitInstance.api.getValidation(
        "token $token"
    )
}
