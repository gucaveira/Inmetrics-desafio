package com.gustavo.rocha.inmetrics.ui.fragment.userdetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
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

    private val args by navArgs<UserDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentUserDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailViewArg = args.detailViewArg

        setSharedElementTransitionOnEnter()

        viewModel.fetchUser(detailViewArg.name)

        viewModel.userGitHubData.observe(viewLifecycleOwner) {
            binding.ReposValues.text = it.publicRepos.toString()
        }

        binding.imgAvatar.run {
            transitionName = detailViewArg.name
            imageLoader.load(this, detailViewArg.imageUrl.plus(".png"))
        }

        binding.userNameLogin.text = detailViewArg.name
    }

    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
            .apply {
                sharedElementEnterTransition = this
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}