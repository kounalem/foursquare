package com.kounalakis.foursquare.datamodel;

import com.kounalakis.foursquare.model.Venue;
import com.kounalakis.foursquare.model.VenueSearchResult;
import com.kounalakis.foursquare.service.FoursquareSearchService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataModelTest {

    @Mock
    FoursquareSearchService foursquareSearchServiceMock;
    @Mock
    Single<VenueSearchResult> venueSearchresultSingle;

    DataModel dataModel;
    private final String QUERY = "LONDON";

    @Before
    public void setUp() throws Exception {
        dataModel = new DataModel(foursquareSearchServiceMock);
    }

    @Test
    public void getVenuesSearchVenuesCalled() {
        foursquareSearchServiceMockSearchVenues();
        dataModel.getVenues(QUERY);
        verify(foursquareSearchServiceMock).searchVenues(QUERY);
    }

    private void foursquareSearchServiceMockSearchVenues() {
        when(foursquareSearchServiceMock.searchVenues(QUERY)).thenReturn(venueSearchresultSingle);
    }

}