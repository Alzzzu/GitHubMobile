package my.first.github.repository

import my.first.github.models.Repo
import my.first.github.models.RepoDetails
import my.first.github.models.UserInfo

class AppRepository {
    suspend fun getRepositories(): List<Repo> {
        // TODO:
        return listOf(Repo("a"))
    }

    suspend fun getRepository(repoId: String): RepoDetails {
        // TODO:
        return RepoDetails("A")
    }

    suspend fun getRepositoryReadme(ownerName: String, repositoryName: String, branchName: String): String {
        // TODO:
        return "A"
    }

    suspend fun signIn(token: String): UserInfo {
        // TODO:
        return UserInfo("f")
    }

    // TODO:
}
