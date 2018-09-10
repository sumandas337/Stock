package com.example.sumandas.stock.injection;

import com.example.sumandas.stock.interfaces.IOrderFulfillManager;
import com.example.sumandas.stock.interfaces.IOrderManager;
import com.example.sumandas.stock.order.FileOrderReciever;
import com.example.sumandas.stock.order.FulfillOrderManager;
import com.example.sumandas.stock.order.OrderManager;
import com.example.sumandas.stock.order.OrderReceiver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    IOrderFulfillManager getFulfillManager(){
        return FulfillOrderManager.initializeManager();
    }

    @Singleton
    @Provides
    IOrderManager getOrderManager(){
        return OrderManager.initializeManager(getFulfillManager());
    }

    @Singleton
    @Provides
    OrderReceiver getOrderReceiver(){
        return new FileOrderReciever(getOrderManager());
    }

}
