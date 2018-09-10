package com.example.sumandas.stock.order;

import com.example.sumandas.stock.Utils;
import com.example.sumandas.stock.interfaces.IOrderBook;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class OrderBook implements IOrderBook {

    public static final int INITIAL_CAPACITY = 11;

    private PriorityQueue<Order> bidQueue;
    private PriorityQueue<Order> sellQueue;
    private String symbol;

    //store the id's of the received orders.
    //this is to keep track if the order is a modify order or cancel order
    //i am assuming for cancel order the initial quantity field is zero
    private HashSet<String> orderIdsReceived;

    public OrderBook(String symbol){
        this.symbol = symbol;
        bidQueue = new PriorityQueue<>(INITIAL_CAPACITY, new BuyComparator());
        sellQueue = new PriorityQueue<>(INITIAL_CAPACITY, new SellComparator());
        orderIdsReceived = new HashSet<>();
    }


    @Override
    public  Order peekBuy() {
        return bidQueue.peek();
    }

    @Override
    public void offerBuy(Order order) {
        bidQueue.offer(order);
    }

    @Override
    public Order peekSell() {
        return sellQueue.peek();
    }

    @Override
    public void offerSell(Order order) {
        sellQueue.offer(order);
    }


    @Override
    public Order pollBuy() {
        return bidQueue.poll();
    }

    @Override
    public Order pollSell() {
        return sellQueue.poll();
    }

    @Override
    public void cancelOrder(Order order) {
        if(order.getOrderType().equals(OrderType.BUY.getValue())){
            bidQueue.remove(order);
        }else {
            sellQueue.remove(order);
        }
    }


    public String getSymbol() {
        return symbol;
    }


    @Override
    public void addOrderId(String orderId) {
        orderIdsReceived.add(orderId);
    }

    @Override
    public void removeOrderId(String orderId) {
        orderIdsReceived.remove(orderId);
    }

    public HashSet<String> getOrderIdsReceived() {
        return orderIdsReceived;
    }


    private class BuyComparator implements Comparator<Order> {
        @Override
        public int compare(Order order1, Order order2) {
            if (order1.getOrderPrice() == order2.getOrderPrice()) {
                return Utils.compareTimestamps(order1.getOrderTime(), order2.getOrderTime());
            }
            return (int) (order1.getOrderPrice() - order2.getOrderPrice())*-1;
        }
    }

    private class SellComparator implements Comparator<Order> {
        @Override
        public int compare(Order order1, Order order2) {
            if (order1.getOrderPrice() == order2.getOrderPrice()) {
                return Utils.compareTimestamps(order1.getOrderTime(), order2.getOrderTime());
            }
            return (int) (order1.getOrderPrice() - order2.getOrderPrice());
        }
    }
}
