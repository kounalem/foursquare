package com.kounalakis.foursquare.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilTest {
    @Mock
    Context contextMock;
    @Mock
    ConnectivityManager connectivityManagerMock;
    @Mock
    NetworkInfo networkInfoMock;

    @Test
    public void isNetworkConnectedWifi() {
        networkInfoTypeWifi();
        assertThat(NetworkUtil.isNetworkConnected(contextMock), is(true));
    }

    @Test
    public void isNetworkConnectedMobile() {
        networkInfoTypeMobile();
        assertThat(NetworkUtil.isNetworkConnected(contextMock), is(true));
    }

    @Test
    public void isNetworkConnectedMobileFalse() {
        networkInfoTypeOffline();
        assertThat(NetworkUtil.isNetworkConnected(contextMock), is(false));
     }

    private void networkInfoTypeWifi() {
        when(contextMock.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManagerMock);
        when((connectivityManagerMock.getActiveNetworkInfo()))
                .thenReturn(networkInfoMock);
        when(networkInfoMock.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);
    }

    private void networkInfoTypeMobile() {
        when(contextMock.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManagerMock);
        when((connectivityManagerMock.getActiveNetworkInfo()))
                .thenReturn(networkInfoMock);
        when(networkInfoMock.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);
    }

    private void networkInfoTypeOffline() {
        when(contextMock.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManagerMock);
        when((connectivityManagerMock.getActiveNetworkInfo()))
                .thenReturn(networkInfoMock);
        when(networkInfoMock.getType()).thenReturn(ConnectivityManager.TYPE_DUMMY);
    }
}