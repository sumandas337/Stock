package com.example.sumandas.stock.order;

public enum  OrderType {

    BUY("buy"),
    SELL("sell"),
    UNKNOWN("UNKNOWN");

    private String value;


    OrderType(String value) {
        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public static OrderType from(String value) {
        for (OrderType orderType : OrderType.values()) {
            if (orderType.getValue().equals(value)) return orderType;
        }

        return OrderType.UNKNOWN;
    }

}

