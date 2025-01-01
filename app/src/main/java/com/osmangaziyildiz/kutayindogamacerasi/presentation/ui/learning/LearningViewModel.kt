package com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.learning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.LearningContent
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.learning.GetLearningContentsUseCase
import com.osmangaziyildiz.kutayindogamacerasi.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LearningViewModel @Inject constructor(
    private val getLearningContentsUseCase: GetLearningContentsUseCase
) : ViewModel() {

    private val _contents = MutableStateFlow<UiState<List<LearningContent>>>(UiState.Loading)
    val contents: StateFlow<UiState<List<LearningContent>>> = _contents.asStateFlow()

    fun fetchLearningContents(categoryId: String) {
        viewModelScope.launch {
            _contents.value = UiState.Loading

            getLearningContentsUseCase(categoryId).onSuccess { contents ->
                _contents.value = UiState.Success(contents)
            }.onFailure { exception ->
                _contents.value = UiState.Error(
                    exception.message ?: "İçerikler yüklenirken bir hata oluştu"
                )
            }
        }
    }
}