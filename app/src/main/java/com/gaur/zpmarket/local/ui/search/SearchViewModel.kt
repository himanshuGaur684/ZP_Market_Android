package com.gaur.zpmarket.local.ui.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {


    private val q = MutableLiveData<String>()


    fun postSearch(q: String) {
        this.q.postValue(q)
    }


    val searchList = q.switchMap {
        searchRepository.getSearchPagingProducts(it).asLiveData().cachedIn(viewModelScope)
    }


}