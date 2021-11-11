package io.lb.roomexample.ui.main

import android.app.SearchManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.lb.roomexample.R
import io.lb.roomexample.model.RepositoryData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RoomExample)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        runOnUiThread {
            shimmerMainRepositories.startShimmer()
        }

        rvRepositories.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter()
        rvRepositories.adapter = adapter
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val context = this

        CoroutineScope(Main).launch {
            viewModel.makeApiCall()
            viewModel.loadRepositories().observe(context) {
                if(it != null) {
                    updateAdapter(it)
                } else {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateAdapter(it: List<RepositoryData>?) {
        adapter.updateList(it)
        adapter.notifyDataSetChanged()

        Handler(Looper.getMainLooper()).postDelayed({
            disableShimmer()
            if (!it.isNullOrEmpty()) {
                rvRepositories.visibility = View.VISIBLE
            } else {
                rvRepositories.visibility = View.GONE
            }
        }, 1500)
    }

    private fun disableShimmer() {
        shimmerMainRepositories.visibility = View.GONE
        shimmerMainRepositories.stopShimmer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.repositories_menu, menu)

        val drawable = menu.getItem(0).icon
        drawable.mutate()

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)

        val textChangeListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                adapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.getFilter().filter(query)
                return true
            }
        }
        searchView.setOnQueryTextListener(textChangeListener)

        return super.onCreateOptionsMenu(menu)
    }

}