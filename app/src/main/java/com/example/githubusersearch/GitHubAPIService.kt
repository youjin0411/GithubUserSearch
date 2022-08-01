package com.example.githubusersearch

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header
import java.lang.reflect.Type
import java.util.*

//https://api.github.com/users/youjin0411
interface GitHubAPIService {
    @GET("/users/{userId}")
    fun getUser(
        @Path("userId") id: String,
        @Header("Authorization") pat: String
    ) : Call<GitHubUser>
}

data class GitHubUser(
    val login: String,
    val id: Int,
    val name: String?,
    val followers : Int,
    val following: Int
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