package com.example.aplikasigithubuser.data.retrofit

import com.example.aplikasigithubuser.data.model.DetailUserResponse
import com.example.aplikasigithubuser.data.model.UserItem
import com.example.aplikasigithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserItem>>
}