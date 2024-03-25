package com.example.aplikasigithubuser.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

object UserHelper {

    const val EXTRA_USERNAME = "extra_username"

    fun ImageView.loadAvatar(itemView: View, avatarUrl: String?) {
        Glide.with(itemView)
            .load(avatarUrl)
            .placeholder(android.R.drawable.ic_menu_report_image)
            .into(this)
    }

}