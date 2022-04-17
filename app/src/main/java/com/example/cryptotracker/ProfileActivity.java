package com.example.cryptotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }



    private void startCryptoSite(){
        Intent intent = new Intent(this, CryptoSiteActivity.class);
        finish();
        startActivity(intent);
    }

    public void startProfileSite(){
        Intent intent = new Intent(this, ProfileActivity.class);
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
        } else if (item.getItemId() == R.id.profile){
            startProfileSite();
        }
        return true;
    }

}