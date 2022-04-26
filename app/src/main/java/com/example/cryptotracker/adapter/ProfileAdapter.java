/*package com.example.cryptotracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotracker.R;
import com.example.cryptotracker.modul.ProfileModel;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<> {
    private ArrayList<ProfileModel> mProfileItemsData;
    private ArrayList<ProfileModel> mProfileItemsDataAll;
    private Context mcontext;
    private int lastPosition = -1;


    ProfileAdapter(Context context, ArrayList<ProfileModel> profileItems){
        this.mProfileItemsData = profileItems;
        this.mProfileItemsDataAll = profileItems;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.activity_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mProfileItemsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

*/
