package com.gustavo.rocha.inmetrics.ui.fragment.listUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.gustavo.rocha.inmetrics.databinding.FragmentUsersListBinding
import com.gustavo.rocha.inmetrics.imageLoader.ImageLoader
import com.gustavo.rocha.inmetrics.ui.fragment.detail.DetailViewArg
import com.gustavo.rocha.inmetrics.ui.fragment.listUser.adapter.UsersListAdapter
import com.gustavo.rocha.inmetrics.ui.fragment.listUser.viewModel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private var _binding: FragmentUsersListBinding? = null
    private val binding: FragmentUsersListBinding get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: UsersViewModel by viewModels()

    private lateinit var usersListAdapter: UsersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentUsersListBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeInitialLoadState()

        lifecycleScope.launch {
            viewModel.usersPagingData(query = "").collect {
                usersListAdapter.submitData(it)
            }
        }

    }

    private fun initAdapter() {
        usersListAdapter = UsersListAdapter(imageLoader) { userDetail ->

            val directions = UsersListFragmentDirections.actionUsersFragmentToDetailFragment(
                userDetail.login,
                DetailViewArg(userDetail.login, userDetail.avatarUrl)
            )

            findNavController().navigate(directions)
        }

        binding.recyclerView.run {
            setHasFixedSize(true)
            adapter = usersListAdapter
        }
    }

    private fun observeInitialLoadState() {
        lifecycleScope.launch {
            usersListAdapter.loadStateFlow.collect { loadState ->
                binding.flipperUsers.displayedChild = when (loadState.refresh) {
                    is LoadState.Loading -> {
                        setShimmerVisibility(true)
                        FLIPPER_CHILD_LOADING
                    }
                    is LoadState.NotLoading -> {
                        setShimmerVisibility(false)
                        FLIPPER_CHILD_USERS
                    }
                    is LoadState.Error -> {
                        setShimmerVisibility(false)
                        binding.includeViewErrorState.buttonRetry.setOnClickListener {
                            usersListAdapter.retry()
                        }
                        FLIPPER_CHILD_ERROR
                    }
                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewUsersLoadingState.shimmerUsers.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_USERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }

}