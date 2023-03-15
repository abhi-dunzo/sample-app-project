package com.example.sampleappproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappproject.databinding.ActivityMainBinding
import com.example.sampleappproject.paging.CharacterPagingAdapter
import com.example.sampleappproject.paging.LoaderAdapter
import com.example.sampleappproject.viewmodel.MainViewModel
import com.example.sampleappproject.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private val pagingDataAdapter : CharacterPagingAdapter = CharacterPagingAdapter()
//TODO ERROR HANDLING IN SCROLL PROGRESS -> error and progress states


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val repository = (application as CharacterApplication).characterRepository
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]
        recyclerView = mainBinding.recyclerView

        pagingDataAdapter.addLoadStateListener { loadState ->
            mainBinding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            mainBinding.progressBar2.isVisible = loadState.source.refresh is LoadState.Loading
            mainBinding.errorTextView.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }


        mainViewModel.list.observe(
            this
        ) {
            pagingDataAdapter.submitData(lifecycle, it)
            recyclerView.adapter = pagingDataAdapter.withLoadStateFooter(footer=LoaderAdapter())

            println(pagingDataAdapter.snapshot().items)
            Log.d( "my",pagingDataAdapter.itemCount.toString())
        }


//        mainViewModel.characters.observe(
//            this
//        ) {
////             adapter = Adapter(it.results)
////            mainBinding.recyclerView.addOnScrollListener(OnScrollListener(layoutManager ,adapter , it.results))
//
//            mainBinding.recyclerView.adapter = adapter
//            Log.d("hi from mainViewModel observer", it.toString())
//        }

//        pageNumber = mainBinding.pageNumber
//        mainBinding.pageNumber.text = mainViewModel.pageNumber.toString()
        mainViewModel.messagesLiveData.observe(
            this
        ) {
            println(it.toString())
            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
        }
        // TODO remove findViewID (binding)
    }
private  fun handleError (loadState: CombinedLoadStates){
    val errorState = loadState.source.append as? LoadState.Error ?: loadState.source.prepend as? LoadState.Error
    errorState.let{
        if (it != null) {
            Toast.makeText(this , "handled error",Toast.LENGTH_SHORT).show()
        }
    }
}



}


