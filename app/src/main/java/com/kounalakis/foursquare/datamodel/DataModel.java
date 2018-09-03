package com.kounalakis.foursquare.datamodel;

import com.kounalakis.foursquare.model.Venue;
import com.kounalakis.foursquare.model.VenueSearchResult;
import com.kounalakis.foursquare.service.FoursquareSearchService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

import static java.util.Collections.unmodifiableList;

public class DataModel implements IDataModel {

    private FoursquareSearchService foursquareSearchService;

    @Inject
    public DataModel(FoursquareSearchService foursquareSearchService)
    {
        this.foursquareSearchService = foursquareSearchService;
    }

    public Single<List<Venue>> getVenues(String query) {
        return foursquareSearchService.searchVenues(query)
                .flatMap((Function<VenueSearchResult, SingleSource<List<Venue>>>) venueSearchResult -> Single.just(unmodifiableList(venueSearchResult.getVenues())));
    }
}
