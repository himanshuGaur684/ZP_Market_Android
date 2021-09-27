package com.gaur.zpmarket.presentation.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.gaur.zpmarket.domain.use_case.search.ProductSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productSearchUseCase: ProductSearchUseCase
) :
    ViewModel() {

    private val q = MutableLiveData<String>()

    fun postSearch(q: String) {
        this.q.postValue(q)
    }

    val searchList = q.switchMap {
        productSearchUseCase.invoke(it).asLiveData().cachedIn(viewModelScope)
    }
}
