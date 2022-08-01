package com.example.githubusersearch

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type
import java.util.*

//https://api.github.com/users/youjin0411
interface GitHubAPIService {
    @GET("/users/{userId}")
    fun getUser(
        @Path("userId") id: String
    ) : Call<GitHubUser>
}

data class GitHubUser(val id: Int, val login: String)

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

}