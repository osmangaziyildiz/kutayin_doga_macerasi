package com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Question
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.QuestionRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.question.GetQuestionsUseCase
import com.osmangaziyildiz.kutayindogamacerasi.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    private val _questions = MutableStateFlow<UiState<Question>>(UiState.Loading)
    val questions: StateFlow<UiState<Question>> = _questions.asStateFlow()

    private var questionIndex = 0
    private var questionsList = emptyList<Question>()

    fun fetchQuestions(categoryId: String) {
        viewModelScope.launch {
            _questions.value = UiState.Loading

            getQuestionsUseCase(categoryId).onSuccess { fetchedQuestions ->
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