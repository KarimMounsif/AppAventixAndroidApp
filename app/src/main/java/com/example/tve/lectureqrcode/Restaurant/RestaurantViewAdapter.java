package com.example.tve.lectureqrcode.Restaurant;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tve.lectureqrcode.BDD.Historique;
import com.example.tve.lectureqrcode.BDD.Restaurant;
import com.example.tve.lectureqrcode.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by TVE on 02/02/2018.
 */

public class RestaurantViewAdapter extends RecyclerView.Adapter<RestaurantViewAdapter.RestaurantViewHolder> {

    private List<Restaurant> listRestaurants;

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        public TextView nomRestaurant, adrRestaurant, telRestaurant, distanceRestaurant;

        public RestaurantViewHolder(View view){
            super(view);
            nomRestaurant = view.findViewById(R.id.tv_nomRestaurant);
            adrRestaurant = view.findViewById(R.id.tv_adrRestaurant);
            telRestaurant = view.findViewById(R.id.tv_telRestaurant);
            distanceRestaurant = view.findViewById(R.id.tv_distanceRestaurant);
        }
    }

    public RestaurantViewAdapter(List<Restaurant> restaurantListe){
        this.listRestaurants = restaurantListe;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_restaurant_recycle_items, parent, false);

        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        Restaurant restaurant = listRestaurants.get(position);
        holder.nomRestaurant.setText(restaurant.getNomRestaurant());
        holder.adrRestaurant.setText(restaurant.getAdrRestaurant());
        holder.telRestaurant.setText("0" + restaurant.getTelRestaurant());
        holder.distanceRestaurant.setText(restaurant.getPositionRestaurant().substring(0,3) + " km");
    }

    @Override
    public int getItemCount() {

        return listRestaurants.size();
    }
}
