package com.example.sampleappproject

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappproject.adapter.Adapter
import com.example.sampleappproject.viewmodel.MainViewModel
import com.example.sampleappproject.viewmodel.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var pageNumber : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = (application as CharacterApplication).characterRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]
        mainViewModel.characters.observe(
            this
        ) {
            val adapter = Adapter(it.results)
            val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
            recyclerView.adapter = adapter
            Log.d("hi from mainViewModel observer", it.toString())
        }

        pageNumber = findViewById(R.id.pageNumber)
        pageNumber.text = mainViewModel.pageNumber.toString()
        mainViewModel.messages.observe(this) {
            println(it.toString())
            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
        }

        prevButton = findViewById(R.id.PrevButton)
        prevButton.setOnClickListener {
            onPrevBtnClick()
        }

        nextButton = findViewById(R.id.NextButton)
        nextButton.setOnClickListener {
            onNextBtnClick()
        }

    }

    private fun onNextBtnClick() {
        mainViewModel.nextData()
        pageNumber.text = mainViewModel.pageNumber.toString()
    }

    private fun onPrevBtnClick() {
        mainViewModel.prevData()
        pageNumber.text = mainViewModel.pageNumber.toString()
    }


}