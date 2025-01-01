package com.osmangaziyildiz.kutayindogamacerasi.di

import com.osmangaziyildiz.kutayindogamacerasi.data.repository.AuthRepositoryImpl
import com.osmangaziyildiz.kutayindogamacerasi.data.repository.CategoryRepositoryImpl
import com.osmangaziyildiz.kutayindogamacerasi.data.repository.LearningRepositoryImpl
import com.osmangaziyildiz.kutayindogamacerasi.data.repository.QuestionRepositoryImpl
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.AuthRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.CategoryRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.LearningRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.QuestionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindLearningRepository(
        impl: LearningRepositoryImpl
    ): LearningRepository

    @Binds
    @Singleton
    abstract fun bindQuestionRepository(
        impl: QuestionRepositoryImpl
    ): QuestionRepository
}