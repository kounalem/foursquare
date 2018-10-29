package com.kounalakis.foursquare.ui;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.kounalakis.foursquare.foursquare.R;
import com.kounalakis.foursquare.model.Venue;
import com.kounalakis.foursquare.schedulers.SchedulerProvider;
import com.kounalakis.foursquare.util.NetworkUtil;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_venues_list)
    RecyclerView recyclerView;
    @BindView(R.id.sv_search_view)
    SearchView searchView;
    @BindView(R.id.fl_loading_overlay)
    FrameLayout loading;
    @BindView(R.id.tv_no_results)
    TextView noResults;
    @BindView(R.id.tv_no_internet)
    TextView noInternet;

    @NonNull
    private CompositeDisposable mCompositeDisposable;
    @Inject
    MainViewModel viewModel;

    @Inject
    SchedulerProvider schedulerProvider;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        AndroidInjection.inject(this);

        if (!NetworkUtil.isNetworkConnected(this)) {
            showNoInternet();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void bind() {
        mCompositeDisposable = new CompositeDisposable();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showLoader();
                mCompositeDisposable.add(viewModel.getVenues(query)
                        .subscribeOn(schedulerProvider.computation())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(a -> setVenues(a), error -> {
                            if (error instanceof UnknownHostException)
                                showLoader();
                            if (error instanceof HttpException && ((HttpException) error).code() == 400)
                                showEmptyListMessage();
                            else
                                showNoInternet();
                        }));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void unBind() {
        mCompositeDisposable.clear();
    }

    private void setVenues(List<Venue> venueList) {
        if (venueList == null || venueList.size() == 0) {
            showEmptyListMessage();
            return;
        }
        showList();
        recyclerView.setAdapter(new VenueAdapter(venueList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showLoader() {
        loading.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showList() {
        loading.setVisibility(View.GONE);
        noInternet.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showEmptyListMessage() {
        loading.setVisibility(View.GONE);
        noInternet.setVisibility(View.GONE);
        noResults.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showNoInternet() {
        loading.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
        noResults.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
}
