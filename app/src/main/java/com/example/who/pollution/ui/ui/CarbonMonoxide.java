package com.example.who.pollution.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.example.who.pollution.R;
import com.example.who.pollution.ui.controller.RestManager;
import com.example.who.pollution.ui.pojo.carbonMonoxide.CarbonMonoxidePojo;
import com.example.who.pollution.ui.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by who on 16.12.2016.
 */

public class CarbonMonoxide extends AppCompatActivity {
    CarbonMonoxidePojo carbonMonoxidePojo;
    private TextView mTextView;
    private RestManager mManager;
    final String TAG = CarbonMonoxide.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = new RestManager();
        setContentView(R.layout.carbon_monoxide_fragment);
        mTextView = (TextView) findViewById(R.id.carbon_monoxide);
        mTextView.setMovementMethod(new ScrollingMovementMethod());
        loadFeed();
    }

    private void loadFeed() {
        if (isNetworkAvailable()) {
            fillData();
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CarbonMonoxide.this, MainActivity.class);
            CarbonMonoxide.this.startActivity(intent);
        }
    }


    public void fillData() {

        Call<CarbonMonoxidePojo> listCall = mManager.getPollutionService().getCarbonMonoxide();
        listCall.enqueue(new Callback<CarbonMonoxidePojo>() {

            @Override
            public void onResponse(Call<CarbonMonoxidePojo> call, Response<CarbonMonoxidePojo> response) {
                if (response.isSuccessful()) {
                    carbonMonoxidePojo = response.body();
                    mTextView.setText(carbonMonoxidePojo.toString());
                } else if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Server error!!!",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CarbonMonoxidePojo> call, Throwable t) {
            }
        });
    }

    private boolean isNetworkAvailable() {
        return Util.isNetworkAvailable(getApplicationContext());
    }
}



