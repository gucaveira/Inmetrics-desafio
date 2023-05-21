package com.gustavo.rocha.inmetrics.ui.fragment.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gustavo.rocha.inmetrics.databinding.FragmentDetailBinding
import com.gustavo.rocha.inmetrics.imageLoader.ImageLoader
import com.gustavo.rocha.inmetrics.ui.fragment.detail.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailViewArg = args.detailViewArg
        binding.includeLayoutDetail.userNameLogin.text = detailViewArg.name

        setSharedElementTransitionOnEnter()
        setImage(detailViewArg)
        observeUiState(detailViewArg)

        detailViewArg.name.apply {
            viewModel.fetchRepos(this)
        }
    }

    private fun setImage(detailViewArg: DetailViewArg) {
        binding.imgAvatar.run {
            transitionName = detailViewArg.name
            imageLoader.load(this, detailViewArg.imageUrl.plus(".png"))
        }
    }

    private fun observeUiState(detailViewArg: DetailViewArg) {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetail.displayedChild = when (uiState) {
                DetailViewModel.UiState.Loading -> {
                    setShimmerVisibility(true)
                    FLIPPER_CHILD_POSITION_LOADING
                }
                is DetailViewModel.UiState.Success -> {
                    binding.includeLayoutDetail.ReposValues.text =
                        uiState.reposUserList.size.toString()

                    setRecyclerView(uiState)
                    setShimmerVisibility(false)
                    FLIPPER_CHILD_POSITION_DETAIL
                }
                is DetailViewModel.UiState.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.fetchRepos(detailViewArg.name)
                    }
                    setShimmerVisibility(false)
                    FLIPPER_CHILD_POSITION_ERROR
                }
            }
        }
    }

    private fun setRecyclerView(uiSate: DetailViewModel.UiState.Success) {
        binding.includeLayoutDetail.RecyclerParentDetail.run {
            setHasFixedSize(true)
            adapter = ReposAdapter(uiSate.reposUserList)
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeLoadingState.shimmerData.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
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

    companion object {
        private const val FLIPPER_CHILD_POSITION_LOADING = 0
        private const val FLIPPER_CHILD_POSITION_DETAIL = 1
        private const val FLIPPER_CHILD_POSITION_ERROR = 2
    }
}