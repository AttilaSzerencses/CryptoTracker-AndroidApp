package com.example.cryptotracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptotracker.CryptoSiteActivity;
import com.example.cryptotracker.NotificationHelper;
import com.example.cryptotracker.R;
import com.example.cryptotracker.modul.CurrencyModel;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.CurrencyViewholder> {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private ArrayList<CurrencyModel> currencyModals;
    private Context context;
    private NotificationHelper mNotificationHelper;
    private int lastPosition = -1;
    private static final String LOG_TAG = CryptoSiteActivity.class.getName();

    public CurrencyRVAdapter(ArrayList<CurrencyModel> currencyModals, Context context) {
        this.currencyModals = currencyModals;
        this.context = context;
        mNotificationHelper = new NotificationHelper(context);
    }

    public void filterList(ArrayList<CurrencyModel> filterllist) {
        currencyModals = filterllist;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<CurrencyModel> filteredList, Context context){
        this.currencyModals = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CurrencyRVAdapter.CurrencyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item, parent, false);
        return new CurrencyRVAdapter.CurrencyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyRVAdapter.CurrencyViewholder holder, int position) {
        CurrencyModel modal = currencyModals.get(position);
        holder.bindTo(modal);

        if(holder.getAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition=holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return currencyModals.size();
    }
    public class CurrencyViewholder extends RecyclerView.ViewHolder {
        private TextView symbolTV, rateTV, nameTV;

        public CurrencyViewholder(@NonNull View itemView) {
            super(itemView);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            nameTV = itemView.findViewById(R.id.idTVName);

        }

        void bindTo(CurrencyModel currentItem){
            nameTV.setText(currentItem.getName());
            rateTV.setText(currentItem.getPrice()+" $");
            symbolTV.setText(currentItem.getSymbol());

            itemView.findViewById(R.id.deleteCrypto).setOnClickListener(view -> {
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotation);
                itemView.findViewById(R.id.deleteCrypto).startAnimation(animation);
                ((CryptoSiteActivity)context).deleteItem(currentItem);
                mNotificationHelper.send("You're deleting " +nameTV.getText()+ " successfully!");
            });

        }
    }
}