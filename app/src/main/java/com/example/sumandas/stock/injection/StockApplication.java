package com.example.sumandas.stock.injection;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


/**
 * Created by sumandas on 29/01/2017.
 */

public class StockApplication extends MultiDexApplication {

    protected IAppComponent mAppComponent;


    protected static Context mApp;

    @Override
    public void onCreate() {
        super.onCreate();

        initAppComponent();


    }

    public void initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .build();
    }

    public IAppComponent getmApplicationComponent() {
        return mAppComponent;
    }

}

