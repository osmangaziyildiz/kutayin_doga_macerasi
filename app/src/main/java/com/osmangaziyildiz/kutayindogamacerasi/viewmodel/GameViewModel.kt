package com.osmangaziyildiz.kutayindogamacerasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmangaziyildiz.kutayindogamacerasi.models.QuestionModel
import com.osmangaziyildiz.kutayindogamacerasi.repositories.QuestionRepository
import com.osmangaziyildiz.kutayindogamacerasi.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: QuestionRepository,
) : ViewModel() {

    private val _questions = MutableStateFlow<UiState<QuestionModel>>(UiState.Loading)
    val questions: StateFlow<UiState<QuestionModel>> = _questions.asStateFlow()

    private var questionIndex = 0
    private var questionsList = emptyList<QuestionModel>()

    fun fetchQuestions(categoryId: String) {
        viewModelScope.launch {
            _questions.value = UiState.Loading

            repository.fetchQuestions(categoryId).onSuccess { fetchedQuestions ->
                    questionsList = fetchedQuestions
                    if (questionsList.isNotEmpty()) {
                        _questions.value = UiState.Success(questionsList[questionIndex])
                    }
                }.onFailure { exception ->
                    _questions.value = UiState.Error(
                        exception.message ?: "Sorular yüklenirken bir hata oluştu"
                    )
                }
        }
    }

    fun moveToNextQuestion() {
        if (questionIndex < questionsList.size - 1) {
            questionIndex++
            _questions.value = UiState.Success(questionsList[questionIndex])
        }
    }

    fun isLastQuestion(): Boolean = questionIndex == questionsList.size - 1
}