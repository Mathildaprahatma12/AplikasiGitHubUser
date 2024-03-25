package com.example.aplikasigithubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.databinding.FragmentFollowingBinding
import com.example.aplikasigithubuser.ui.home.MainAdapter
import com.example.aplikasigithubuser.utils.UserHelper.EXTRA_USERNAME

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private var username: String? = null
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleIntent()
        initiateViewModel()
        getFollowing()
        observeFollowing()
        observeLoading()
        observeErrorMessage()
    }

    private fun getBundleIntent() {
        username = activity?.intent?.getStringExtra(EXTRA_USERNAME)
    }

    private fun initiateViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
    }

    private fun getFollowing() {
        username?.let { detailViewModel.getFollowing(it) }
    }

    private fun observeFollowing() {
        detailViewModel.following.observe(viewLifecycleOwner) { followers ->
            binding.apply {
                mainAdapter = MainAdapter(followers)
                rvFollowing.adapter = mainAdapter
                rvFollowing.layoutManager = LinearLayoutManager(requireContext())
                rvFollowing.isVisible = !followers.isNullOrEmpty()
                tvEmpty.isVisible = followers.isNullOrEmpty()
            }
        }
    }

    private fun observeLoading() {
        detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbLoading.isVisible = isLoading
        }
    }

    private fun observeErrorMessage() {
        detailViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}