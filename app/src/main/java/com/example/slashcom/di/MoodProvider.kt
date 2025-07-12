package com.example.slashcom.di

import com.example.slashcom.data.mood.MoodRepositoryImpl
import com.example.slashcom.domain.repository.MoodRepository
import com.example.slashcom.utils.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MoodProvider {
    
    private val BASE_URL = "https://web-production-eeedc.up.railway.app/"
    
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    
    @Provides
    fun provideMoodRepository(apiService: ApiService): MoodRepository {
        return MoodRepositoryImpl(apiService)
    }
}
