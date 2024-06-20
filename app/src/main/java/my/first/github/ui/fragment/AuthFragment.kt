package my.first.github.ui.fragment

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import my.first.github.R
import my.first.github.databinding.FragmentAuthBinding
import my.first.github.ui.viewmodel.AuthViewModel
import my.first.github.utils.Constants
import my.first.github.utils.DialogHelper.Companion.buildDialog
import my.first.github.utils.PreferencesManager
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModels()
    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentAuthBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners(){

        binding.et.setOnFocusChangeListener { v, hasFocus ->
            binding.et.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.blue))
            binding.hint.visibility = View.VISIBLE

        }

        binding.button.setOnClickListener {
            if (validateString(binding.et.text.toString())){
                validateToken(binding.et.text.toString())
            }
            else{
                binding.et.backgroundTintList = ColorStateList.valueOf(Color.RED)
            }
        }
    }

    private fun validateString(token: String) : Boolean{
        return "\\S+".toRegex().matches(token)
    }

    private fun validateToken(token: String){
        viewModel.updateToken(token)
        viewModel.onSignButtonPressed()
        viewModel.state.observe(viewLifecycleOwner, Observer { response ->
            binding.progressBar.visibility = View.GONE
            binding.button.text = null
            when(response){
                is AuthViewModel.State.Idle ->{
                    binding.button.text = resources.getString(R.string.sign_in)
                    preferencesManager.putString(Constants.KEY_AUTH_TOKEN, binding.et.text.toString())
                    binding.button.findNavController().apply{
                        try{
                        navigate(R.id.action_authFragment2_to_repositoriesListFragment2)
                        }
                        catch (e:Exception){
                            popBackStack()
                            navigate(R.id.repositoriesListFragment2)
                        }
                    }
                }
                is AuthViewModel.State.InvalidInput ->{
                    binding.button.text = resources.getString(R.string.sign_in)
                    binding.et.hint = response.reason
                    binding.hint.setTextColor(resources.getColor(R.color.red))
                    binding.exception.visibility = View.VISIBLE
                    binding.hint.visibility = View.VISIBLE
                    binding.et.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red))
                }
                is AuthViewModel.State.Error ->{
                    binding.progressBar.visibility = View.VISIBLE
                    buildDialog(requireContext(), response.error)
                }
                else ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.et.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.dark_grey))
                    binding.hint.setTextColor(resources.getColor(R.color.light_grey))
                }
            }
        })
    }
}