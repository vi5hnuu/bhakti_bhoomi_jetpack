package com.vi5hnu.bhaktibhoomi.di


import com.google.gson.GsonBuilder
import com.vi5hnu.bhaktibhoomi.network.BhagvadGeetaAPI
import com.vi5hnu.bhaktibhoomi.repository.BhagvadGeetaRepository
import com.vi5hnu.bhaktibhoomi.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideBhagvadGeetaAPI():BhagvadGeetaAPI{
        return Retrofit
            .Builder()
            .baseUrl(Constants.BHAGVAD_GEETA_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BhagvadGeetaAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideBhagvadGeetaRepository(bhagvadGeetaAPI: BhagvadGeetaAPI):BhagvadGeetaRepository{
        return BhagvadGeetaRepository(bhagvadGeetaAPI);
    }
}