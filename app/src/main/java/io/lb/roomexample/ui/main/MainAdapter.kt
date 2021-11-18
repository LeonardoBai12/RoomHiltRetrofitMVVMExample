package io.lb.roomexample.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import io.lb.roomexample.R
import io.lb.roomexample.model.RepositoryData
import kotlinx.android.synthetic.main.row_repository.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var repositories: List<RepositoryData>? = null
    private var repositoriesFiltered: List<RepositoryData>? = null

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

        val requestOptions = RequestOptions.fitCenterTransform()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(holder.ivRepositoryOwner.width,holder.ivRepositoryOwner.height)

        Glide.with(holder.ivRepositoryOwner).load(data?.owner?.avatarUrl)
            .apply(requestOptions).into(holder.ivRepositoryOwner)
    }

    fun getFilter(): Filter {
        val filter : Filter
        filter = object : Filter(){
            override fun performFiltering(filter: CharSequence): FilterResults {
                var typedFilter = filter
                val results = FilterResults()

                if (typedFilter.isEmpty()) {
                    repositoriesFiltered = repositories

                    results.count = repositoriesFiltered?.size ?: 0
                    results.values = repositoriesFiltered
                    return results
                }

                val filteredItems = ArrayList<RepositoryData>()
                repositoriesFiltered?.forEach { data ->
                    typedFilter = typedFilter.toString().lowercase()

                    val name = data.name?.lowercase() ?: ""
                    val description = data.description?.lowercase() ?: ""

                    if (description.contains(typedFilter) || name.contains(typedFilter)) {
                        filteredItems.add(data)
                    }
                }

                results.count = filteredItems.size
                results.values = filteredItems
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                repositories = results.values as List<RepositoryData>?
                notifyDataSetChanged()
            }
        }
        return filter
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