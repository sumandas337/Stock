package com.example.sumandas.stock.interfaces;

import com.example.sumandas.stock.order.FulfillOrder;

public interface IOrderFulfillManager {

     void onOrderFulfilled(FulfillOrder fulfillOrder);
     void setOutput(IOutputFulfilledOrders output);
}
