package com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Category
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.category.GetCategoriesUseCase
import com.osmangaziyildiz.kutayindogamacerasi.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<Category>>> = _categories.asStateFlow()

    fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = UiState.Loading
            getCategoriesUseCase().onSuccess { categories ->
                _categories.value = UiState.Success(categories)
            }.onFailure { exception ->
                _categories.value = UiState.Error(
                    exception.message ?: "Kategoriler yüklenirken bir hata oluştu"
                )
            }
        }
    }
}