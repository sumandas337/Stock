package com.example.sumandas.stock;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.sumandas.stock.injection.StockApplication;
import com.example.sumandas.stock.interfaces.IOrderFulfillManager;
import com.example.sumandas.stock.interfaces.IOutputFulfilledOrders;
import com.example.sumandas.stock.order.FulfillOrder;
import com.example.sumandas.stock.order.OrderReceiver;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements IOutputFulfilledOrders {

    @Inject OrderReceiver orderReceiver;
    @Inject IOrderFulfillManager fulfillManager;

    TextView fulfilledOrders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        fulfilledOrders = findViewById(R.id.fulfilledOrders);
        setSupportActionBar(toolbar);
        final RxPermissions rxPermissions = new RxPermissions(this);

        ((StockApplication)getApplication()).getmApplicationComponent().
                injectActivity(this);
       fulfillManager.setOutput(this);

        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(granted -> {
            if (granted) {
                orderReceiver.getOrders();
            }
        });


    }

    @Override
    public void output(FulfillOrder order) {
        String previousOrders =fulfilledOrders.getText().toString();
        previousOrders = previousOrders+order.toString();
        fulfilledOrders.setText(previousOrders);
    }
}
