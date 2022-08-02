package com.example.githubusersearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubUserRepositoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_hub_user_repository_list)

        var result = findViewById<RecyclerView>(R.id.lstUser)
        val id = intent.getStringExtra("id")!!
//        result.text = "아이디 :${id}"
        Log.d("mytag", id)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(GitHubAPIService::class.java)
        val apiCallForData = apiService.getRepos(
            id,
            "token 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"
        )
        apiCallForData.enqueue(object : Callback<List<GitHubRepo>> {
            override fun onResponse(call: Call<List<GitHubRepo>>, response: Response<List<GitHubRepo>>) {
                val data = response.body()!!
                Log.d("mytag", data.toString())

                var listview = findViewById<RecyclerView>(R.id.lstUser)
                listview.layoutManager = LinearLayoutManager(this@GitHubUserRepositoryListActivity)
                listview.adapter = GitHubAdapter(data)
                listview.setHasFixedSize(true)
            }
            override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
            }
        })

    }
}