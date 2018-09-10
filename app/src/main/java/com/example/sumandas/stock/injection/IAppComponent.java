package com.example.sumandas.stock.injection;

import com.example.sumandas.stock.MainActivity;
import com.example.sumandas.stock.interfaces.IOrderFulfillManager;
import com.example.sumandas.stock.interfaces.IOrderManager;
import com.example.sumandas.stock.order.FulfillOrderManager;
import com.example.sumandas.stock.order.OrderReceiver;

public interface IAppComponent {

    IOrderFulfillManager getFulfillManager();
    IOrderManager getOrderManager();
    OrderReceiver getOrderReceiver();

    void injectActivity(MainActivity mainActivity);
}
