package com.example.sumandas.stock.order;

import com.example.sumandas.stock.interfaces.IOrderManager;

public abstract class OrderReceiver {

    private IOrderManager orderManager;

    public OrderReceiver(IOrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public abstract void getOrders();

    public void onOrderReceived(Order order) {
        if(order.getOrderType().equals(OrderType.BUY.getValue())){
            orderManager.onBuyOrderReceived(order);
        }else if(order.getOrderType().equals(OrderType.SELL.getValue())){
            orderManager.onSellOrderReceived(order);
        }
    }
}
