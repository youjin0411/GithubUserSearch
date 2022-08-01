package com.example.githubusersearch

import android.graphics.drawable.Drawable
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header
import retrofit2.http.Url
import java.lang.reflect.Type
import java.util.*

//https://api.github.com/users/youjin0411
interface GitHubAPIService {
    @GET("/users/{userId}")
    fun getUser(
        @Path("userId") id: String,
        @Header("Authorization") pat: String
    ) : Call<GitHubUser>

    @GET("/users/{userId}/repos")
    fun getRepos(
        @Path("userId") id : String,
        @Header("Authorization") pat: String
    ):Call<List<GitHubRepo>>
}

data class UserData(
    var id: String,
    val name: String
)

data class GitHubRepo(
   val name : String,
   val html_url: String,
   val description : String?,
   val forks_count : Int,
   val watchers_count : Int,
   val stargazers_count: Int
)

data class GitHubUser(
    val login: String,
    val id: Int,
    val name: String?,
    val followers : Int,
    val following: Int,
    @SerializedName("avatar_url") //코틀린에서는 카멜 케이스를 사용함으로 SerializedName을 통해서 아래의 avatarUrl을 사용함
    val avatarUrl: String
)

/*
class GitHubUserDeserializer: JsonDeserializer<GitHubUser> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GitHubUser {
        val root = json?.getAsJsonObject()
        val id = root?.getAsJsonPrimitive("id")?.asInt //asInt타입변환
        val login = root?.getAsJsonPrimitive("login")?.asString

        return GitHubUser(id!!, login!!)
    }

}*/