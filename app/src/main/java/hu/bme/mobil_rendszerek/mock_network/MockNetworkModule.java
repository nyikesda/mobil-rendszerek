package hu.bme.mobil_rendszerek.mock_network;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.mobil_rendszerek.network.API;
import hu.bme.mobil_rendszerek.network.NetworkConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nyikes on 2017. 04. 22..
 */

@Module
public class MockNetworkModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(1, TimeUnit.SECONDS)
                .addInterceptor(new FakeInterceptor())
                .build();
        return new Retrofit.Builder()
                .baseUrl(NetworkConfig.ENDPOINT_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public API provideAPI(Retrofit retrofit) {
        return retrofit.create(API.class);
    }
}
