package my.first.github.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import my.first.github.R
import my.first.github.adapter.RepositoriesAdapter
import my.first.github.adapter.onRepoItemClick
import my.first.github.databinding.FragmentAuthBinding
import my.first.github.databinding.FragmentRepositoriesListBinding
import my.first.github.models.RepoItem
import my.first.github.repository.AppRepository
import my.first.github.retrofit.GithubAPI
import my.first.github.ui.viewmodel.AuthViewModel
import my.first.github.ui.viewmodel.RepositoriesListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list), onRepoItemClick {

    private lateinit var binding: FragmentRepositoriesListBinding
    private val viewModel: RepositoriesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRepositoriesListBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setListeners()
        getRepositories()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners(){
        binding.exit.setOnClickListener {

                findNavController().apply {
                    popBackStack()
                    navigate(R.id.authFragment2)
                }
            }

        binding.button.setOnClickListener {
            getRepositories()
        }
    }

    private fun getRepositories(){
        viewModel.getRepos()
        viewModel.state.observe(viewLifecycleOwner, Observer { response ->
            binding.progressBar2.visibility = View.VISIBLE
            binding.image.visibility = View.GONE
            binding.problem.visibility = View.GONE
            binding.explanation.visibility = View.GONE
            binding.button.visibility = View.GONE
            when (response) {
                is RepositoriesListViewModel.State.Loaded -> {
                    binding.repoList.apply {
                        binding.progressBar2.visibility = View.GONE
                        visibility = View.VISIBLE
                        adapter = RepositoriesAdapter(response.repos, this@RepositoriesListFragment, requireContext())
                        layoutManager = LinearLayoutManager(activity)
                    }
                }
                is RepositoriesListViewModel.State.Empty ->{
                    binding.repoList.visibility = View.GONE
                    binding.progressBar2.visibility = View.GONE
                    binding.problem.setTextColor(resources.getColor(R.color.blue))
                    binding.image.setBackgroundResource(R.drawable.ic_empty)
                    binding.problem.text = resources.getString(R.string.empty)
                    binding.explanation.text = resources.getString(R.string.no_repos)
                    binding.button.text = resources.getString(R.string.refresh)
                    binding.button.visibility = View.VISIBLE
                    binding.image.visibility = View.VISIBLE
                    binding.problem.visibility = View.VISIBLE
                    binding.explanation.visibility = View.VISIBLE
                }

                is RepositoriesListViewModel.State.Error -> {
                    binding.repoList.visibility = View.GONE
                    binding.progressBar2.visibility = View.GONE
                    binding.problem.setTextColor(resources.getColor(R.color.red))
                    if (response.error == "no connection"){
                        binding.image.setBackgroundResource(R.drawable.ic_no_connection)
                        binding.problem.text = resources.getString(R.string.connection)
                        binding.explanation.text = resources.getString(R.string.check_connection)
                        binding.button.visibility = View.VISIBLE
                        binding.image.visibility = View.VISIBLE
                        binding.problem.visibility = View.VISIBLE
                        binding.explanation.visibility = View.VISIBLE
                    }
                    else {
                        binding.image.setBackgroundResource(R.drawable.ic_error)
                        "${response.error} error".also { binding.problem.text = it }
                        "Check your${response.error}".also { binding.explanation.text = it }
                        binding.button.visibility = View.VISIBLE
                        binding.image.visibility = View.VISIBLE
                        binding.problem.visibility = View.VISIBLE
                        binding.explanation.visibility = View.VISIBLE
                    }
                }
                else -> {
                }
            }
        })
    }

    override fun onClick(repo: RepoItem) {
        val bundle = Bundle().apply {
            putSerializable("repo", repo)
        }
        findNavController().navigate(R.id.detailInfoFragment2, bundle)
    }
}