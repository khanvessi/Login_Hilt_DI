package com.example.logintask.di

import android.app.Application
import androidx.room.Room
import com.example.logintask.data.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton
import javax.security.auth.callback.Callback

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callBack: UserDatabase.CallBack
    ) = Room.databaseBuilder(app, UserDatabase::class.java,"user_database")
        .fallbackToDestructiveMigration()
        .addCallback(callBack)
        .build()

    @Provides
    fun provideUserDao(db: UserDatabase) = db.userDao()

    //THIS IS NOT JUST COROUTINE SCOPE BUT THIS IS THE APPLICATION SCOPE
    @ApplicationScope

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob())

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

}
