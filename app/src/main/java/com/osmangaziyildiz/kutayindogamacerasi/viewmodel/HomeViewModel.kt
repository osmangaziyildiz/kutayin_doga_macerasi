package com.osmangaziyildiz.kutayindogamacerasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmangaziyildiz.kutayindogamacerasi.models.CategoryModel
import com.osmangaziyildiz.kutayindogamacerasi.repositories.CategoryRepository
import com.osmangaziyildiz.kutayindogamacerasi.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CategoryRepository,
) : ViewModel() {

    private val _categories = MutableStateFlow<UiState<List<CategoryModel>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<CategoryModel>>> = _categories.asStateFlow()

    fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = UiState.Loading

            repository.fetchCategories().onSuccess { categories ->
                    _categories.value = UiState.Success(categories)
                }.onFailure { exception ->
                    _categories.value = UiState.Error(
                        exception.message ?: "Kategoriler yüklenirken bir hata oluştu"
                    )
                }
        }
    }
}