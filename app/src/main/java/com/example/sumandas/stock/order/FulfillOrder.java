package com.example.sumandas.stock.order;

public class FulfillOrder {

    public String sellOrderId;
    public int sellQuantity;
    public float sellPrice;
    public String buyOrderId;

    public FulfillOrder(String sellOrderId, int sellQuantity, float sellPrice, String buyOrderId) {
        this.sellOrderId = sellOrderId;
        this.sellQuantity = sellQuantity;
        this.sellPrice = sellPrice;
        this.buyOrderId = buyOrderId;
    }


    @Override
    public String toString() {
        return  sellOrderId + "  " +
                sellQuantity + "  "+
                sellPrice + "  "+
                buyOrderId + "\n";
    }
}
