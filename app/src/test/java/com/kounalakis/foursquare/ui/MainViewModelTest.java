package com.kounalakis.foursquare.ui;

import com.kounalakis.foursquare.datamodel.IDataModel;
import com.kounalakis.foursquare.model.Venue;
import com.kounalakis.foursquare.schedulers.ISchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {
    @Mock
    IDataModel iDataModelMock;
    @Mock
    ISchedulerProvider iSchedulerProviderMock;
    @Mock
    Single<List<Venue>> venueSearchResultSingle;

    private final String QUERY = "LONDON";
    MainViewModel mainViewModel;

    @Before
    public void setUp() throws Exception {
        mainViewModel = new MainViewModel(iDataModelMock);
    }

    @Test
    public void getVenuesModelVenuesisCalled() {
        mainViewModel.getVenues(QUERY);
        verify(iDataModelMock).getVenues(QUERY);
    }

    @Test
    public void getVenuesReturnsVenues() {
        searchVenuesVenue();
        assertThat(mainViewModel.getVenues(QUERY), is(venueSearchResultSingle));
    }

    private void searchVenuesVenue() {
        when(iDataModelMock.getVenues(QUERY)).
                thenReturn(venueSearchResultSingle);
    }

}