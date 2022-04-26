package com.example.cryptotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cryptotracker.adapter.CurrencyRVAdapter;
import com.example.cryptotracker.modul.CurrencyModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CryptoSiteActivity extends AppCompatActivity {

    private static final String LOG_TAG = CryptoSiteActivity.class.getName();
    private FirebaseUser user;
    private EditText serachEdt;
    private RecyclerView currenciesRV;
    private ProgressBar loadingPB;
    private ArrayList<CurrencyModel> currencyModelArrayList;
    private CurrencyRVAdapter currencyRVAdapter;
    private NotificationHelper mNotificationHelper;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private int limit = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_site);

        serachEdt = findViewById(R.id.idEdtCurrency);
        currenciesRV = findViewById(R.id.idRVcurrency);
        loadingPB = findViewById(R.id.idPBLoading);
        currencyModelArrayList = new ArrayList<>();
        currencyRVAdapter = new CurrencyRVAdapter(currencyModelArrayList, this);
        currenciesRV.setLayoutManager(new LinearLayoutManager(this));
        currenciesRV.setAdapter(currencyRVAdapter);
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Crypto_Currencies");
        queryData();



        serachEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterCurrencies(editable.toString());
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Log.d(LOG_TAG, "Authenticated user!");
        } else{
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

        mNotificationHelper = new NotificationHelper(this);
    }

    private void filterCurrencies(String currency){
        ArrayList<CurrencyModel> filteredList = new ArrayList<>();
        for(CurrencyModel item : currencyModelArrayList){
            if (item.getName().toLowerCase().contains(currency.toLowerCase())){
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No currency found for searched query", Toast.LENGTH_LONG).show();
        } else {
            currencyRVAdapter.filterList(filteredList);
        }
    }

    private void initializeData() {
        // Get the resources from the XML file.
        String[] itemsName = getResources()
                .getStringArray(R.array.crypto_names);
        String[] itemsSymbol = getResources()
                .getStringArray(R.array.crypto_symbol);
        String[] itemsPrice = getResources()
                .getStringArray(R.array.crypto_price);
        for (int i = 0; i < itemsName.length; i++) {
            mItems.add(new CurrencyModel(
                    itemsName[i],
                    itemsSymbol[i],
                    itemsPrice[i]
                    ));
        }
    }

    private void queryData(){
        currencyModelArrayList.clear();
        mItems.limit(limit).orderBy("symbol",Query.Direction.ASCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                CurrencyModel item = document.toObject(CurrencyModel.class);
                item.setId(document.getId());
                currencyModelArrayList.add(item);
            }

            if (currencyModelArrayList.size() == 0) {
                initializeData();
                queryData();
            }

            // Notify the adapter of the change.
            currencyRVAdapter.notifyDataSetChanged();
        });
    }

    private void queryDataOrdered(){
        currencyModelArrayList.clear();
        mItems.limit(limit).orderBy("symbol",Query.Direction.DESCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                CurrencyModel item = document.toObject(CurrencyModel.class);
                item.setId(document.getId());
                currencyModelArrayList.add(item);
            }

            if (currencyModelArrayList.size() == 0) {
                initializeData();
                queryDataOrdered();
            }
            currencyRVAdapter.notifyDataSetChanged();
        });
    }


    public void deleteItem(CurrencyModel item) {
        DocumentReference ref = mItems.document(item._getId());
        ref.delete()
                .addOnSuccessListener(success -> {
                    Log.d(LOG_TAG, "Item is successfully deleted: " + item._getId());
                })
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, "Item " + item._getId() + " cannot be deleted.", Toast.LENGTH_LONG).show();
                });
        queryData();
    }

    public void startCryptoSite(){
        Intent intent = new Intent(this, CryptoSiteActivity.class);
        finish();
        startActivity(intent);
    }

    public void startProfileSite(){
        Intent intent = new Intent(this, InsertCryptoAcitvity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.log_out_button){
            FirebaseAuth.getInstance().signOut();
            finish();
        } else if (item.getItemId() == R.id.crypto_currencies){
            startCryptoSite();
        } else if (item.getItemId() == R.id.addNewCryptoCurrency){
            startProfileSite();
        } else if (item.getItemId() == R.id.orderByZA){
            queryDataOrdered();
        }
        return true;
    }


}