package com.kounalakis.foursquare.schedulers;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SchedulerProviderTest {
    SchedulerProvider schedulerProvider;

    @Before
    public void setUp() throws Exception {
        schedulerProvider = new SchedulerProvider();
    }

    @Test
    public void providesComputation() {
        assertThat(schedulerProvider.computation(), is(Schedulers.computation()));
    }

    @Test
    public void providesUI() {
        assertThat(schedulerProvider.ui(), is(AndroidSchedulers.mainThread()));
    }
}