package my.first.github.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import my.first.github.R
import my.first.github.databinding.FragmentAuthBinding
import my.first.github.ui.viewmodel.AuthViewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {
    lateinit var binding: FragmentAuthBinding
    lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAuthBinding.inflate(inflater,container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(AuthViewModel::class.java)

        super.onViewCreated(view, savedInstanceState)
    }
}