package com.sky.skygomovie.di

import com.sky.skygomovie.api.ApiHelper
import com.sky.skygomovie.api.ApiHelperImpl
import com.sky.skygomovie.api.ApiService
import com.sky.skygomovie.api.FakeApiService
import com.sky.skygomovie.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
@Module
class FakeAppModule {

    @Provides
    @Singleton
    fun provideApiService(fakeApiService: FakeApiService) = ApiService

//    @Provides
//    @Singleton
//    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}