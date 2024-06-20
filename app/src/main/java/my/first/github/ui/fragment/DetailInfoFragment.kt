package my.first.github.ui.fragment

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import my.first.github.R
import my.first.github.databinding.FragmentDetailInfoBinding
import my.first.github.databinding.FragmentRepositoriesListBinding
import my.first.github.models.RepoItem
import my.first.github.ui.viewmodel.RepositoriesListViewModel
import my.first.github.ui.viewmodel.RepositoryInfoViewModel

@AndroidEntryPoint
class DetailInfoFragment : Fragment(R.layout.fragment_detail_info) {

    private lateinit var binding: FragmentDetailInfoBinding
    private val viewModel: RepositoryInfoViewModel by viewModels()
    private val detailInfoFragmentArgs: DetailInfoFragmentArgs  by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailInfoBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repoItem = detailInfoFragmentArgs.repo
        loadInfo(repoItem)
        setListeners(repoItem)
        getReadme(repoItem)
    }

    private fun loadInfo(repoItem: RepoItem){
        binding.title.text = repoItem.name
        binding.link.text = repoItem.html_url
        binding.watchers.text = repoItem.watchers_count.toString()
        binding.stars.text = repoItem.stargazers_count.toString()
        binding.forks.text = repoItem.forks.toString()
        binding.license.text =  if (repoItem.license != null) repoItem.license.name else resources.getString(R.string.no_license)
    }

    private fun setListeners(repoItem: RepoItem) {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.exit.setOnClickListener {

            findNavController().apply {
                popBackStack()
                navigate(R.id.authFragment2)
            }
        }
        binding.button.setOnClickListener {
            binding.progressBar3.visibility = View.VISIBLE
            getReadme(repoItem)
        }
    }

    private fun getReadme(repoItem: RepoItem){
        viewModel.getReadme(repoItem)
        viewModel.state.observe(viewLifecycleOwner, Observer { response ->
            binding.image.visibility = View.GONE
            binding.problem.visibility = View.GONE
            binding.explanation.visibility = View.GONE
            binding.readme.visibility = View.GONE
            binding.button.visibility = View.GONE
            binding.progressBar3.visibility = View.VISIBLE

            when(response){
                is RepositoryInfoViewModel.ReadmeState.Loaded ->{
                    binding.progressBar3.visibility = View.GONE
                    binding.readme.visibility = View.VISIBLE
                    response.markdown.also {
                        binding.readme.text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    }
                }
                is RepositoryInfoViewModel.ReadmeState.Error ->{
                    binding.progressBar3.visibility = View.GONE
                    binding.problem.setTextColor(resources.getColor(R.color.red))
                        binding.image.setBackgroundResource(R.drawable.ic_no_connection)
                        binding.problem.text = resources.getString(R.string.connection)
                        binding.explanation.text = resources.getString(R.string.check_connection)
                        binding.button.visibility = View.VISIBLE
                        binding.image.visibility = View.VISIBLE
                        binding.problem.visibility = View.VISIBLE
                        binding.explanation.visibility = View.VISIBLE

                }
                is RepositoryInfoViewModel.ReadmeState.Empty ->{
                    binding.progressBar3.visibility = View.GONE
                    binding.readme.text = resources.getString(R.string.no_readme)
                }
                else ->{
                }
            }

        })
    }
}