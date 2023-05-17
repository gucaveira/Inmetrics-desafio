package com.gustavo.rocha.inmetrics.ui.fragment.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gustavo.rocha.inmetrics.databinding.FragmentUserDetailBinding
import com.gustavo.rocha.inmetrics.imageLoader.ImageLoader
import com.gustavo.rocha.inmetrics.ui.fragment.userdetail.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: UserDetailViewModel by viewModels()

    private var _binding: FragmentUserDetailBinding? = null
    private val binding: FragmentUserDetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentUserDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchUser("gucaveira")

        viewModel.userGitHubData.observe(viewLifecycleOwner) {
            imageLoader.load(binding.imgAvatar, it.avatarUrl.plus(".png"))
            binding.userNameLogin.text = it.name
            binding.ReposValues.text = it.publicRepos.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}