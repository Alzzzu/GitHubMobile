package my.first.github.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import my.first.github.databinding.ItemContainerRepositoryBinding
import my.first.github.models.RepoItem
import okio.buffer
import okio.source
import org.json.JSONObject
import java.nio.charset.Charset

class RepositoriesAdapter(
    private val allRepositories: List<RepoItem>,
    val onRepoItemClick: onRepoItemClick,
    val context: Context
) : RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>() {

    private val TAG = "AdapterTag"

    inner class RepositoryViewHolder(private val binding: ItemContainerRepositoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun setData(repoItem: RepoItem){
            binding.name.text = repoItem.name
            binding.language.text = repoItem.language
            binding.description.text = repoItem.description
            try{
                val source = context.assets.open("colors.json").source().buffer()
                val json = source.readByteString().string(Charset.forName("utf-8"))
                val colors  = JSONObject(json)
                binding.language.setTextColor(Color.parseColor(
                    colors.getJSONObject(binding.language.text.toString()).getString("color")))
            }
            catch (e: Exception){
                Log.e(TAG, e.message.toString())
            }
            binding.root.setOnClickListener {
                onRepoItemClick.onClick(repoItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding: ItemContainerRepositoryBinding = ItemContainerRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoryViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return allRepositories.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repo = allRepositories[position]
        holder.setData(repo)
    }
}
 interface onRepoItemClick{
     fun onClick(repo: RepoItem)
 }
