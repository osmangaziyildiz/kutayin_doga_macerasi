package com.osmangaziyildiz.kutayindogamacerasi.di

import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.AuthRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.CategoryRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.LearningRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.QuestionRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.auth.LoginUseCase
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.auth.SignUpUseCase
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.category.GetCategoriesUseCase
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.learning.GetLearningContentsUseCase
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.question.GetQuestionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: AuthRepository
    ): LoginUseCase = LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        repository: AuthRepository
    ): SignUpUseCase = SignUpUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(
        repository: CategoryRepository
    ): GetCategoriesUseCase = GetCategoriesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetLearningContentsUseCase(
        repository: LearningRepository
    ): GetLearningContentsUseCase = GetLearningContentsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetQuestionsUseCase(
        repository: QuestionRepository
    ): GetQuestionsUseCase = GetQuestionsUseCase(repository)
}