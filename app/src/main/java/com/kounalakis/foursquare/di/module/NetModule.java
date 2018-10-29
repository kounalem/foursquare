package com.kounalakis.foursquare.di.module;

import android.app.Application;
import android.content.Context;

import com.kounalakis.foursquare.ui.MainViewModel;
import com.kounalakis.foursquare.datamodel.DataModel;
import com.kounalakis.foursquare.datamodel.IDataModel;
import com.kounalakis.foursquare.schedulers.ISchedulerProvider;
import com.kounalakis.foursquare.schedulers.SchedulerProvider;
import com.kounalakis.foursquare.service.FoursquareRetrofitApiService;
import com.kounalakis.foursquare.service.FoursquareSearchService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {
    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        client.connectTimeout(15, TimeUnit.SECONDS);
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.foursquare.com/v2/")
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    FoursquareSearchService FoursquareSearchService(Retrofit retrofit, Context context) {
        FoursquareRetrofitApiService client = retrofit.create(FoursquareRetrofitApiService.class);
        return new FoursquareSearchService(client, context);
    }

    @Provides
    public IDataModel provideDataModel(FoursquareSearchService foursquareSearchService) {
        return new DataModel(foursquareSearchService);
    }

    @Provides
    @Singleton
    public ISchedulerProvider provideScheduler() {
        return new SchedulerProvider();
    }

    @Provides
    @Singleton
    public MainViewModel provideMainViewModel(IDataModel dataModel) {
        return new MainViewModel(dataModel);
    }
}