package com.kounalakis.foursquare.di.component;

import android.app.Application;

import com.kounalakis.foursquare.FourSquareApplication;
import com.kounalakis.foursquare.di.ActivityBindingModule;
import com.kounalakis.foursquare.di.module.ApplicationModule;
import com.kounalakis.foursquare.di.module.NetModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ApplicationModule.class, NetModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface ApplicationComponent extends AndroidInjector<FourSquareApplication> {

    // Gives us syntactic sugar, we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();
    }
}