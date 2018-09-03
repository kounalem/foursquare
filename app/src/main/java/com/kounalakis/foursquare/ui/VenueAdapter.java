package com.kounalakis.foursquare.ui;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kounalakis.foursquare.foursquare.R;
import com.kounalakis.foursquare.model.Venue;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueViewHolder> {

    private final List<Venue> venues;

    public VenueAdapter(List<Venue> venues) {
        this.venues = venues;
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new VenueViewHolder(inflater.inflate(R.layout.layout_venue, parent, false));
    }

    @Override
    public void onBindViewHolder(VenueViewHolder holder, int position) {
        holder.name.setText(venues.get(position).getName());
        holder.ratingBar.setRating(venues.get(position).getRating());
        holder.location.setText(venues.get(position).getLocation().getAddress());
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    static class VenueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_venue_name)
        TextView name;
        @BindView(R.id.tv_location)
        TextView location;
        @BindView(R.id.rb_rating)
        AppCompatRatingBar ratingBar;

        VenueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
