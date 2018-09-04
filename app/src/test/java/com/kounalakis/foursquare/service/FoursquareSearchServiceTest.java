package com.kounalakis.foursquare.service;

import android.content.Context;

import com.kounalakis.foursquare.foursquare.R;
import com.kounalakis.foursquare.model.VenueSearchResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoursquareSearchServiceTest {

    @Mock
    Context contextMock;
    @Mock
    FoursquareRetrofitApiService apiServiceMock;
    @Mock
    Single<VenueSearchResult> venueSearchResultSingle;

    FoursquareSearchService foursquareSearchService;

    private final String QUERY = "LONDON";

    @Before
    public void setUp() throws Exception {
        foursquareSearchService = new FoursquareSearchService(apiServiceMock, contextMock);
        searchVenuesVenue();
    }

    @Test
    public void searchVenuesIsCalled() {
        foursquareSearchService.searchVenues(QUERY);
        verify(apiServiceMock).searchVenues(contextMock.getString(R.string.client_id), contextMock.getString(R.string.client_secret), QUERY, contextMock.getString(R.string.api_version));
    }

    @Test
    public void searchVenuesReturnsObservable() {
        foursquareSearchService.searchVenues(QUERY);
        assertThat(foursquareSearchService.searchVenues(QUERY), is(venueSearchResultSingle));
    }

    private void searchVenuesVenue() {
        when(apiServiceMock.searchVenues(contextMock.getString(R.string.client_id), contextMock.getString(R.string.client_secret), QUERY, contextMock.getString(R.string.api_version))).
                thenReturn(venueSearchResultSingle);
    }

}