package io.lb.roomexample.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.lb.roomexample.R
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = MainAdapter()
        recyclerview.adapter = recyclerViewAdapter
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.loadRepositories().observe(this) {
            if(!it.isNullOrEmpty()) {
                recyclerViewAdapter.updateList(it)
                recyclerViewAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "error in getting data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.makeApiCall()
    }
}