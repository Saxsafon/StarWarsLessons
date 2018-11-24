package design.alex.starwars.di.module;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import design.alex.starwars.Constants;
import design.alex.starwars.data.rest.RestApiPeoples;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Модуль для работы с сетью
 */
@Module
public class NetModule {

    public NetModule() { }

    /**
     * Получение интерфейса для работы с Api персонажей
     * @param retrofit - объект ретрофита
     * @return RestApiPeoples
     */
    @Singleton
    @Provides
    RestApiPeoples getRestApiPeoples(Retrofit retrofit) {
        return retrofit.create(RestApiPeoples.class);
    }

    /**
     * Получение объекта ретрофита
     * @return Retrofit
     */
    @Singleton
    @Provides
    Retrofit getRetrofit(OkHttpClient client) {
        return new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                message -> Log.d("TAG", message)
        );
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client.addInterceptor(interceptor);
        return client.build();
    }
}
