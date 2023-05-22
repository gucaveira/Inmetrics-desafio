package com.gustavo.rocha.inmetrics.ui.fragment.listUser

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.gustavo.rocha.inmetrics.R
import com.gustavo.rocha.inmetrics.databinding.FragmentUsersListBinding
import com.gustavo.rocha.inmetrics.imageLoader.ImageLoader
import com.gustavo.rocha.inmetrics.ui.fragment.detail.DetailViewArg
import com.gustavo.rocha.inmetrics.ui.fragment.listUser.adapter.UsersListAdapter
import com.gustavo.rocha.inmetrics.ui.fragment.listUser.viewModel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersListFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    private var _binding: FragmentUsersListBinding? = null
    private val binding: FragmentUsersListBinding get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: UsersViewModel by viewModels()

    private lateinit var usersListAdapter: UsersListAdapter

    private lateinit var searchView: SearchView

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

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UsersViewModel.UiState.SearchResult -> {
                    lifecycleScope.launch {
                        usersListAdapter.submitData(uiState.data)
                    }
                }
            }
        }

        viewModel.searchUser()
    }

    private fun initAdapter() {
        usersListAdapter = UsersListAdapter(imageLoader) { userDetail ->

            val directions = UsersListFragmentDirections.actionUsersFragmentToDetailFragment(
                userDetail.login,
                DetailViewArg(userDetail.login, userDetail.avatarUrl)
            )

            findNavController().navigate(directions)
        }

        binding.usersRecyclerView.run {
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


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            else -> false
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.users_menu_items, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        searchView = searchItem.actionView as SearchView

        searchItem.setOnActionExpandListener(this)

        if (viewModel.currentSearchQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(viewModel.currentSearchQuery, false)
        }

        searchView.run {

            isSubmitButtonEnabled = true

            setOnQueryTextListener(this@UsersListFragment)
        }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return query?.let { text ->
            viewModel.currentSearchQuery = text
            viewModel.searchUser()
            view?.let { activity?.hideKeyboard(it) }
            true
        } ?: false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        viewModel.closeSearch()
        viewModel.searchUser()
        return true
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