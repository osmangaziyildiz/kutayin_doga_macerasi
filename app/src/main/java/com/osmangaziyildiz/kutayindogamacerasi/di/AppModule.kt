package com.osmangaziyildiz.kutayindogamacerasi.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.osmangaziyildiz.kutayindogamacerasi.repositories.AuthRepository
import com.osmangaziyildiz.kutayindogamacerasi.repositories.CategoryRepository
import com.osmangaziyildiz.kutayindogamacerasi.repositories.LearningRepository
import com.osmangaziyildiz.kutayindogamacerasi.repositories.QuestionRepository
import com.osmangaziyildiz.kutayindogamacerasi.utility.AssetAudioPlayer
import com.osmangaziyildiz.kutayindogamacerasi.utility.AudioPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firestore: FirebaseFirestore): AuthRepository {
        return AuthRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(firestore: FirebaseFirestore): CategoryRepository {
        return CategoryRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideContentRepository(firestore: FirebaseFirestore): LearningRepository {
        return LearningRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(firestore: FirebaseFirestore): QuestionRepository {
        return QuestionRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideAudioPlayer(@ApplicationContext context: Context): AudioPlayer {
        return AssetAudioPlayer(context)
    }

}