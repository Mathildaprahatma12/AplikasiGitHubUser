package com.example.aplikasigithubuser.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.databinding.ActivityDetailUserBinding
import com.example.aplikasigithubuser.utils.UserHelper.EXTRA_USERNAME
import com.example.aplikasigithubuser.utils.UserHelper.loadAvatar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private var username: String? = null
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getBundleIntent()
        initiateViewModel()
        initiateView()
        setupViewPager()
        getDetailUser()
        observeDetailUser()
        observeLoading()
        observeErrorMessage()
    }

    private fun getBundleIntent() {
        username = intent.getStringExtra(EXTRA_USERNAME)
    }

    private fun initiateViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
    }

    private fun initiateView() {
        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getDetailUser() {
        username?.let { detailViewModel.getDetailUser(it) }
    }

    private fun observeDetailUser() {
        detailViewModel.detailUser.observe(this) { detailUser ->
            binding.apply {
                imgAvatar.loadAvatar(binding.root, detailUser.avatarUrl)
                tvName.text = detailUser.name
                tvUsername.text = detailUser.login
                tvFollowers.text = getString(R.string.followers, detailUser.followers.toString())
                tvFollowing.text = getString(R.string.following, detailUser.following.toString())
            }
        }
    }

    private fun observeLoading() {
        detailViewModel.isLoading.observe(this) { isLoading ->
            binding.pbLoading.isVisible = isLoading
        }
    }

    private fun observeErrorMessage() {
        detailViewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}