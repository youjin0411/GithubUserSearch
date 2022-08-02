package com.example.githubusersearch

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class GitHubAdapter(val dataList: List<GitHubRepo>)
    :RecyclerView.Adapter<GitHubAdapter.ItemViewHolder>()
{
        class ItemViewHolder(val view: View)
            :RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = dataList[position]

        holder.view.findViewById<TextView>(R.id.repo_name).text = "프로젝트명 : ${data.name} 💨\n"
        holder.view.findViewById<TextView>(R.id.repo_description).text = "설명 : ${data.description}\n"
        holder.view.findViewById<TextView>(R.id.repo_forks_count).text = "${data.forks_count}\n"
        holder.view.findViewById<TextView>(R.id.repo_stargazers_count).text = "${data.stargazers_count}\n"
        holder.view.findViewById<TextView>(R.id.repo_watchers_count).text = "${data.watchers_count}\n"

        holder.view.setOnClickListener {
            var intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(data.html_url)
            )
            holder.view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.githublist
    }

}