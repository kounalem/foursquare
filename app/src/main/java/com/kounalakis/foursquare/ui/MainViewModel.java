package com.kounalakis.foursquare.ui;

import android.support.annotation.NonNull;

import com.kounalakis.foursquare.datamodel.IDataModel;
import com.kounalakis.foursquare.model.Venue;
import com.kounalakis.foursquare.schedulers.ISchedulerProvider;
import com.kounalakis.foursquare.schedulers.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * View model for the main activity.
 */
public class MainViewModel {

    IDataModel dataModel;
    ISchedulerProvider mSchedulerProvider;

    @Inject
    public MainViewModel(IDataModel dataModel,
                         ISchedulerProvider schedulerProvider) {
        this.dataModel = dataModel;
        this.mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    public Single<List<Venue>> getVenues(String query) {
        return dataModel.getVenues(query);
    }

}
