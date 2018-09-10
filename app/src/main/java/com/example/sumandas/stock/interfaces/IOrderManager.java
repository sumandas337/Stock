package com.example.sumandas.stock.interfaces;

import com.example.sumandas.stock.order.Order;
import com.example.sumandas.stock.order.OrderBook;

public interface IOrderManager {

    void onBuyOrderReceived(Order order);
    void onSellOrderReceived(Order order);

    OrderBook getOrderBookForSymbol(String symbol);

}
