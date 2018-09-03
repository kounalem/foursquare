package com.kounalakis.foursquare.datamodel;

import com.kounalakis.foursquare.model.Venue;

import java.util.List;

import io.reactivex.Single;

public interface IDataModel {
    Single<List<Venue>> getVenues(String query);
}
