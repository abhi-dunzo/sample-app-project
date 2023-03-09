package com.example.sampleappproject.viewmodel

import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappproject.models.CharacterList
import com.example.sampleappproject.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CharacterRepository):ViewModel() {
    var pageNumber  = 1;
    init{
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getCharacters(1)
            }
            catch (e:Exception){
                e.printStackTrace()
                messages.postValue("catched the error")
            }
        }
    }

    val characters  : LiveData<CharacterList>
    get() = repository.characters
    val messages : MutableLiveData<String>
    get() = repository.messages

    fun nextData() {
        pageNumber++;
        viewModelScope.launch(Dispatchers.IO) {
            try {
               repository.getCharacters(pageNumber)
            }
            catch (e:Exception){
                messages.postValue("catched the error")
                e.printStackTrace()
            }
        }
    }
    fun prevData() {
        if(pageNumber > 1 ) {
            pageNumber--;
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getCharacters(pageNumber)
                }
                catch (e:Exception){
                    messages.postValue("catched the error")
                    e.printStackTrace()
                }
            }
        }
        else{
            messages.postValue("already on the last page")
           Log.d("last page = $pageNumber" , "Error you are already on the last page $pageNumber")
        }
    }
}