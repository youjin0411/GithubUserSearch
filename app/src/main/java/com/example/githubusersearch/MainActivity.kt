package com.example.githubusersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userIdInput = findViewById<EditText>(R.id.user_id_input)
        val content = findViewById<TextView>(R.id.content)
        val imageView = findViewById<ImageView>(R.id.avatar_img)

        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com")
            .addConverterFactory(
                GsonConverterFactory.create(
//                    GsonBuilder().registerTypeAdapter(
//                        GitHubUser::class.java,
//                        GitHubUserDeserializer()
//                    ).create()
                )
            ).build()

        val apiService = retrofit.create(GitHubAPIService::class.java)

        val classInfo: Class<GitHubAPIService> = GitHubAPIService::class.java
        findViewById<Button>(R.id.search_btn).setOnClickListener{
            val id = userIdInput.text.toString()
            val apiCallForData = apiService.getUser(id,"token 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")
            apiCallForData.enqueue(object : Callback<GitHubUser>{
                override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                    val data= response.body()!!
                    Log.d("mytag", data.toString())

                    content.text = "login: ${data.login}\nid: ${data.id}\nname: ${data.name}"
                    Glide.with(this@MainActivity)
                        .load(data?.avatar_url)
                        .into(imageView)
                }

                override fun onFailure(call: Call<GitHubUser>, t: Throwable) {}
            })
        }

    }
}