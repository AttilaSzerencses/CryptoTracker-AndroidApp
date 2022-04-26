package com.example.cryptotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cryptotracker.modul.CurrencyModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class InsertCryptoAcitvity extends AppCompatActivity {
    private FirebaseUser user;
    private static final String LOG_TAG = CryptoSiteActivity.class.getName();
    private EditText nameEditText;
    private EditText symbolEditText;
    private EditText priceEditText;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_crypto);
        mContext=this.getApplicationContext();


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Log.d(LOG_TAG, "Authenticated user!");
        } else{
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

        nameEditText = findViewById(R.id.nameEditText);
        symbolEditText = findViewById(R.id.symbolEditText);
        priceEditText = findViewById(R.id.priceEditText);
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Crypto_Currencies");

    }


    public void cancel(View view) {
        startCryptoSite();
    }

    public void addNewCryptoCurrency(View view){

        if (this.nameEditText.getText().toString().isEmpty()){
            this.nameEditText.setError("It can't be empty!");
            return;
        }
        if (this.symbolEditText.getText().toString().isEmpty()){
            this.symbolEditText.setError("It can't be empty!");
            return;
        }
        if (this.priceEditText.getText().toString().isEmpty()){
            this.priceEditText.setError("It can't be empty!");
            return;
        }

        mItems.add(new CurrencyModel(
           nameEditText.getText().toString(),
           symbolEditText.getText().toString(),
           priceEditText.getText().toString()
        ));
        startCryptoSite();
    }


    private void startCryptoSite(){
        Intent intent = new Intent(this, CryptoSiteActivity.class);
        finish();
        startActivity(intent);
    }

    public void startAddNewCryptoSite(){
        Intent intent = new Intent(this, InsertCryptoAcitvity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.crypto_menu, menu);
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
            startAddNewCryptoSite();
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "You leave Crypto add site.", Toast.LENGTH_LONG).show();
    }
}