package com.example.sumandas.stock.interfaces;

import com.example.sumandas.stock.order.Order;

public interface IOrderBook {

    Order peekBuy();
    Order peekSell();

    void offerBuy(Order order);
    void offerSell(Order order);

    Order pollBuy();
    Order pollSell();

    void addOrderId(String orderId);

    void removeOrderId(String orderId);

    void cancelOrder(Order order);

}
