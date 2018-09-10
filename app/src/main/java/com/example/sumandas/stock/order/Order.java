package com.example.sumandas.stock.order;

public class Order {

    public Order(String orderId, String orderTime, String symbol, String orderType, int orderQuantity, float orderPrice) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.symbol = symbol;
        this.orderType = orderType;
        this.orderInitialQuantity = orderQuantity;
        this.orderRemainingQuantity = orderInitialQuantity;
        this.orderPrice = orderPrice;
    }

    private String orderId;
    private String orderTime;
    private String symbol;
    private String orderType;
    private int orderInitialQuantity;
    private float orderPrice;
    private int orderRemainingQuantity;


    public String getOrderId() {
        return orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOrderType() {
        return orderType;
    }

    public int getOrderInitialQuantity() {
        return orderInitialQuantity;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public int getOrderRemainingQuantity() {
        return orderRemainingQuantity;
    }

    public void setOrderRemainingQuantity(int orderRemainingQuantity) {
        this.orderRemainingQuantity = orderRemainingQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return ((Order) o).orderId.equals(orderId);
    }



}
