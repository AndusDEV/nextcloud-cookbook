package de.lukasneugebauer.nextcloudcookbook.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.lukasneugebauer.nextcloudcookbook.BuildConfig
import de.lukasneugebauer.nextcloudcookbook.api.BasicAuthInterceptor
import de.lukasneugebauer.nextcloudcookbook.api.NextcloudApi
import de.lukasneugebauer.nextcloudcookbook.data.models.nutrition.NutritionDeserializer
import de.lukasneugebauer.nextcloudcookbook.data.models.nutrition.NutritionNw
import de.lukasneugebauer.nextcloudcookbook.data.PreferencesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val authInterceptor =
            BasicAuthInterceptor(BuildConfig.NC_USERNAME, BuildConfig.NC_APP_PASSWORD)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(NutritionNw::class.java, NutritionDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.NC_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideRestaurantApi(retrofit: Retrofit): NextcloudApi =
        retrofit.create(NextcloudApi::class.java)

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager =
        PreferencesManager(context)
}