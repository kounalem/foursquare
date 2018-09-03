package com.kounalakis.foursquare.service;

import com.kounalakis.foursquare.model.VenueSearchResult;

import io.reactivex.Single;

public interface LocationSearchService {
    Single<VenueSearchResult> searchVenues(String query);
}
