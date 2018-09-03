package com.kounalakis.foursquare.service;

import android.content.Context;

import com.kounalakis.foursquare.foursquare.R;
import com.kounalakis.foursquare.model.VenueSearchResult;

import io.reactivex.Single;

public class FoursquareSearchService implements LocationSearchService {

    Context context;
    private FoursquareRetrofitApiService apiService;

    public FoursquareSearchService(FoursquareRetrofitApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public Single<VenueSearchResult> searchVenues(String location) {
        return apiService.searchVenues(context.getString(R.string.client_id), context.getString(R.string.client_secret), location, context.getString(R.string.api_version));
    }

}