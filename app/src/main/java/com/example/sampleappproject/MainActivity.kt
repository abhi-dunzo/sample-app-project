package com.example.sampleappproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.api.RetrofitHelper
import com.example.sampleappproject.repository.CharacterRepository
import com.example.sampleappproject.viewmodel.MainViewModel
import com.example.sampleappproject.viewmodel.MainViewModelFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = (application as CharacterApplication).characterRepository
        mainViewModel = ViewModelProvider(this , MainViewModelFactory(repository)).get(MainViewModel::class.java)
        mainViewModel.characters.observe(this
        ) {
            val adapter = Adapter(it.results)
            val recyclerView : RecyclerView = findViewById(R.id.recycler_view)
            recyclerView.adapter = adapter
            Log.d("hi from mainviewmodel observer", it.toString())
            Log.d("HLOO", "hi")

        }

    }
}