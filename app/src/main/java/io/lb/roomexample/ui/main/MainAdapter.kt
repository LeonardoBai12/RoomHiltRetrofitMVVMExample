package io.lb.roomexample.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.lb.roomexample.R
import io.lb.roomexample.model.RepositoryData
import kotlinx.android.synthetic.main.row_repository.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var repositories: List<RepositoryData>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_repository, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = repositories?.get(position)

        holder.tvRepositoryName.text = data?.name
        holder.tvRepositoryDescription.text = data?.description

        Glide.with(holder.ivRepositoryOwner)
            .load(data?.owner?.avatarUrl)
            .into(holder.ivRepositoryOwner)
    }

    override fun getItemCount(): Int {
        return repositories?.size ?: 0
    }

    fun updateList(listData: List<RepositoryData>?) {
        this.repositories = listData
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivRepositoryOwner: ImageView = view.ivRepositoryOwner
        val tvRepositoryName: TextView = view.tvRepositoryName
        val tvRepositoryDescription: TextView = view.tvRepositoryDescription
    }
}