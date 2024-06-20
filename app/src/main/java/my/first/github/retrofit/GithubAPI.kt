package my.first.github.retrofit

import my.first.github.models.Readme
import my.first.github.models.RepoItem
import my.first.github.models.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubAPI {

    @GET("user/repos")
    suspend fun getReposList(
        @Header("Authorization") token: String,

        //   @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        ): Response<List<RepoItem>>

    @GET("user")
    suspend fun getValidation(
        @Header("Authorization") token: String,
    ): Response<UserInfo>

    @GET("repos/{owner}/{repositoryName}/contents/README.md")
    suspend fun getRepositoryReadme(
        @Path("owner") ownerName: String,
        @Path("repositoryName") repositoryName: String,
    ): Response<Readme>
}
