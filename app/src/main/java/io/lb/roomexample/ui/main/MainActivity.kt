package io.lb.roomexample.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.lb.roomexample.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RoomExample)

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
            if(it != null) {
                recyclerViewAdapter.updateList(it)
                recyclerViewAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }

        CoroutineScope(IO).launch {
            viewModel.makeApiCall()
        }.invokeOnCompletion {
            viewModel.loadRepositories()
        }
    }
}