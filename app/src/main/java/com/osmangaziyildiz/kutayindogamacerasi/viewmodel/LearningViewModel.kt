package com.osmangaziyildiz.kutayindogamacerasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmangaziyildiz.kutayindogamacerasi.models.LearningContentModel
import com.osmangaziyildiz.kutayindogamacerasi.repositories.LearningRepository
import com.osmangaziyildiz.kutayindogamacerasi.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val repository: LearningRepository,
) : ViewModel() {

    private val _contents = MutableStateFlow<UiState<List<LearningContentModel>>>(UiState.Loading)
    val contents: StateFlow<UiState<List<LearningContentModel>>> = _contents.asStateFlow()

    fun fetchLearningContents(categoryId: String) {
        viewModelScope.launch {
            _contents.value = UiState.Loading

            repository.fetchLearningContents(categoryId).onSuccess { contents ->
                _contents.value = UiState.Success(contents)
            }.onFailure { exception ->
                _contents.value = UiState.Error(exception.message ?: "Bilinmeyen bir hata oluştu")
            }
        }
    }
}