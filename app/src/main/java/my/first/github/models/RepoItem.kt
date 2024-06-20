@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package my.first.github.models

import java.io.Serializable

data class RepoItem(
    val id: Int,
    val git_url: String,
    val html_url: String,
    val license: License? = null,
    val name: String,
    val owner: Owner,
    val size: Int,
    val stargazers_count: Int,
    val watchers_count: Int,
    val forks: Int,
    val language: String? = null,
    val description: String? = null,
) : Serializable