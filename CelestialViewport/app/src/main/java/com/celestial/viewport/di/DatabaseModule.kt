package com.celestial.viewport.di

import android.content.Context
import com.celestial.viewport.data.dao.*
import com.celestial.viewport.data.database.CelestialDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CelestialDatabase =
        CelestialDatabase.getDatabase(context)

    @Provides fun provideUserDao(db: CelestialDatabase): UserDao = db.userDao()
    @Provides fun provideLessonDao(db: CelestialDatabase): LessonDao = db.lessonDao()
    @Provides fun provideQuizDao(db: CelestialDatabase): QuizDao = db.quizDao()
    @Provides fun provideQuizQuestionDao(db: CelestialDatabase): QuizQuestionDao = db.quizQuestionDao()
    @Provides fun provideQuizResultDao(db: CelestialDatabase): QuizResultDao = db.quizResultDao()
}
