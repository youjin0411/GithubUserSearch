package com.example.githubusersearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
        val userbtn = findViewById<Button>(R.id.usersave_button)

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
        findViewById<Button>(R.id.search_btn).setOnClickListener {
            var id = userIdInput.text.toString()
            if (id.isBlank()) {
                Toast.makeText(this, "유저 아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                val apiCallForData =
                    apiService.getUser(id, "token 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")

                userbtn.setOnClickListener {
                    val intent = Intent(this, GitHubUserRepositoryListActivity::class.java)
                    intent.putExtra("id", userIdInput.text.toString())
                    startActivity(intent)
                }
                apiCallForData.enqueue(object : Callback<GitHubUser> {
                    override fun onResponse(
                        call: Call<GitHubUser>,
                        response: Response<GitHubUser>
                    ) {
                        if (response.code().toString().startsWith("4")) {
                            Toast.makeText(this@MainActivity, "유저가 없습니다.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Log.d("mytag", response.code().toString())
                            findViewById<Button>(R.id.usersave_button).visibility = View.VISIBLE
                            val data = response.body()!!
                            Log.d("mytag", data.toString())

                            content.text =
                                "login: ${data.login}\nid: ${data.id}\nname: ${data.name}"
                            Glide.with(this@MainActivity)
                                .load(data?.avatarUrl)
                                .into(imageView)
                        }
                    }

                    override fun onFailure(call: Call<GitHubUser>, t: Throwable) {}
                })
            }
        }
    }
}