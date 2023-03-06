package com.example.sampleappproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleappproject.models.CharacterList
import com.example.sampleappproject.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CharacterRepository):ViewModel() {

    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacters()
        }
    }
    val characters  : LiveData<CharacterList>
    get() = repository.characters
}