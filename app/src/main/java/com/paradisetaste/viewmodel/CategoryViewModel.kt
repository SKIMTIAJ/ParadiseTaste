package com.paradisetaste.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradisetaste.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    val categoryDetailsLiveData = repository.categoryDetailsLiveData

    fun getCategoryDetails(type:String){
        viewModelScope.launch {
            repository.getCategoryDetails(type)
        }
    }




}