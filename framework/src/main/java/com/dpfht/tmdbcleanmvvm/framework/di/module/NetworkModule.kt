package com.dpfht.tmdbcleanmvvm.framework.di.module

import android.content.Context
import com.dpfht.tmdbcleanmvvm.framework.BuildConfig
import com.dpfht.tmdbcleanmvvm.data.datasource.AppDataSource
import com.dpfht.tmdbcleanmvvm.framework.Config
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest.AuthInterceptor
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.RemoteDataSourceImpl
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest.RestService
import com.dpfht.tmdbcleanmvvm.framework.data.datasource.remote.rest.UnsafeOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  fun provideBaseUrl(): String {
    return Config.BASE_URL
  }

  @Provides
  @Singleton
  fun provideCertificatePinner(): CertificatePinner {
    return CertificatePinner.Builder()
      .add("api.themoviedb.org", "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
      .add("api.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
      .add("api.themoviedb.org", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
      .add("api.themoviedb.org", "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
      .build()
  }

  @Provides
  @Singleton
  fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  @Provides
  @Singleton
  fun provideClient(@ApplicationContext context: Context, certificatePinner: CertificatePinner): OkHttpClient {
    if (BuildConfig.DEBUG) {
      return UnsafeOkHttpClient.getUnsafeOkHttpClient(context)
    }

    val httpClientBuilder = OkHttpClient()
      .newBuilder()
      .certificatePinner(certificatePinner)

    httpClientBuilder.addInterceptor(AuthInterceptor())

    return httpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
  }

  @Provides
  @Singleton
  fun provideRemoteDataSource(@ApplicationContext context: Context, restService: RestService): AppDataSource {
    return RemoteDataSourceImpl(context, restService)
  }
}
