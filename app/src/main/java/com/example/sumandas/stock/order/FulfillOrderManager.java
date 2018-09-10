package com.example.sumandas.stock.order;

import com.example.sumandas.stock.interfaces.IOrderFulfillManager;
import com.example.sumandas.stock.interfaces.IOutputFulfilledOrders;

public class FulfillOrderManager implements IOrderFulfillManager {

    private static volatile FulfillOrderManager sFulfillManager;
    private IOutputFulfilledOrders output;

    public static FulfillOrderManager initializeManager() {
        if (sFulfillManager == null) {
            synchronized (FulfillOrderManager.class) {
                if (sFulfillManager == null) {
                    sFulfillManager = new FulfillOrderManager();
                }
            }
        }
        return sFulfillManager;
    }

    public void setOutput(IOutputFulfilledOrders output){
        this.output = output;
    }


    @Override
    public void onOrderFulfilled(FulfillOrder fulfillOrder) {
        output.output(fulfillOrder);
    }
}
