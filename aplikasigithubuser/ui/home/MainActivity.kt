package com.example.aplikasigithubuser.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.databinding.ActivityMainBinding
import com.example.aplikasigithubuser.ui.detail.DetailUserActivity
import com.example.aplikasigithubuser.utils.UserHelper.EXTRA_USERNAME

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initiateViewModel()
        initiateSearchView()
        observeUsers()
        observeLoading()
        observeErrorMessage()
    }

    private fun initiateViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
    }

    private fun initiateSearchView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    val searchText = searchView.text.toString()
                    if (searchText.isNotEmpty()) {
                        mainViewModel.getUsers(searchText)
                    } else {
                        mainViewModel.getUsers()
                    }
                    false
                }
        }
    }

    private fun observeUsers() {
        mainViewModel.users.observe(this) { users ->
            mainAdapter = MainAdapter(users) { username ->
                startActivity(
                    Intent(this, DetailUserActivity::class.java).putExtra(
                        EXTRA_USERNAME,
                        username
                    )
                )
            }
            binding.apply {
                rvUser.adapter = mainAdapter
                rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
                rvUser.isVisible = !users.isNullOrEmpty()
                tvEmpty.isVisible = users.isNullOrEmpty()
            }
        }
    }

    private fun observeLoading() {
        mainViewModel.isLoading.observe(this) { isLoading ->
            binding.pbLoading.isVisible = isLoading
        }
    }

    private fun observeErrorMessage() {
        mainViewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}