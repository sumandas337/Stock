package com.example.sumandas.stock.order;

import com.example.sumandas.stock.interfaces.IOrderFulfillManager;
import com.example.sumandas.stock.interfaces.IOrderManager;

import java.util.HashMap;

public class OrderManager implements IOrderManager {

    private HashMap<String, OrderBook> bookMap;
    private IOrderFulfillManager fulfillManager;
    private static volatile OrderManager sOrderManager;

    public static OrderManager initializeManager(IOrderFulfillManager fulfillManager) {
        if (sOrderManager == null) {
            synchronized (OrderManager.class) {
                if (sOrderManager == null) {
                    sOrderManager = new OrderManager(fulfillManager);
                }
            }
        }
        return sOrderManager;
    }

    private OrderManager(IOrderFulfillManager fulfillManager){
        this.fulfillManager = fulfillManager;
        bookMap = new HashMap<>();
    }

    @Override
    public void onBuyOrderReceived(Order order) {
        OrderBook orderBook = getOrderBookForSymbol(order.getSymbol());
        boolean isOrderCancelled = false;
        if(orderBook.getOrderIdsReceived().contains(order.getOrderId())){// is a modify/cancel order
            if(order.getOrderInitialQuantity() == 0){// is a cancel order
                orderBook.cancelOrder(order);
                isOrderCancelled = true;
            }else{// is a modify order so remove older order and treat as new order
                orderBook.cancelOrder(order);//remove older order
            }
        }
        if(!isOrderCancelled){// new/modified order so check if it can be satisfied otherwise put in buy queue
            Order sellOrder = orderBook.peekSell();
            // keep polling sell queue till buy can be satisfied
            while (sellOrder!=null && sellOrder.getOrderPrice()<= order.getOrderPrice() && order.getOrderRemainingQuantity()>0){
                int sellRemaining = sellOrder.getOrderRemainingQuantity();
                int buyRemaining = order.getOrderRemainingQuantity();
                if( sellRemaining == buyRemaining){
                    // both buy and sell orders extinguished
                    orderBook.pollSell();
                    orderBook.removeOrderId(sellOrder.getOrderId());
                    order.setOrderRemainingQuantity(0);
                    fulfillManager.onOrderFulfilled(new FulfillOrder(sellOrder.getOrderId(), sellRemaining,
                            sellOrder.getOrderPrice(),order.getOrderId()));

                }else if(sellRemaining > buyRemaining){
                    // only buy order extinguished
                    sellOrder.setOrderRemainingQuantity(sellRemaining - buyRemaining);
                    fulfillManager.onOrderFulfilled(new FulfillOrder(sellOrder.getOrderId(), buyRemaining,
                            sellOrder.getOrderPrice(),order.getOrderId()));
                    order.setOrderRemainingQuantity(0);

                }else{
                    // only sell order extinguished
                    orderBook.pollSell();
                    orderBook.removeOrderId(sellOrder.getOrderId());
                    order.setOrderRemainingQuantity(buyRemaining - sellRemaining);
                    fulfillManager.onOrderFulfilled(new FulfillOrder(sellOrder.getOrderId(), sellRemaining,
                            sellOrder.getOrderPrice(),order.getOrderId()));
                    // get new sell order
                    sellOrder = orderBook.peekSell();
                }
            }
            if(order.getOrderRemainingQuantity() >0){// add buy to queue
                orderBook.offerBuy(order);
                orderBook.addOrderId(order.getOrderId());
            }
        }

    }

    @Override
    public void onSellOrderReceived(Order order) {
        OrderBook orderBook = getOrderBookForSymbol(order.getSymbol());
        boolean isOrderCancelled = false;
        if(orderBook.getOrderIdsReceived().contains(order.getOrderId())){// is a modify/cancel order
            if(order.getOrderInitialQuantity() == 0){// is a cancel order
                orderBook.cancelOrder(order);
                isOrderCancelled = true;
            }else{// is a modify order so add to queue. no need to test buy queue;
                orderBook.cancelOrder(order);//remove older order
            }
        }
        if(!isOrderCancelled){// new/modified order so check if it can be satisfied otherwise put in sell queue
            Order buyOrder = orderBook.peekBuy();
            // keep polling buy queue till buy can be satisfied
            while (buyOrder!=null && buyOrder.getOrderPrice()>= order.getOrderPrice() && order.getOrderRemainingQuantity()>0){
                int buyRemaining = buyOrder.getOrderRemainingQuantity();
                int sellRemaining = order.getOrderRemainingQuantity();
                if( sellRemaining == buyRemaining){
                    // both buy and sell orders extinguished
                    orderBook.pollBuy();
                    orderBook.removeOrderId(buyOrder.getOrderId());
                    order.setOrderRemainingQuantity(0);
                    fulfillManager.onOrderFulfilled(new FulfillOrder(order.getOrderId(), sellRemaining,
                            order.getOrderPrice(),buyOrder.getOrderId()));
                }else if(sellRemaining < buyRemaining){
                    // only sell order extinguished
                    buyOrder.setOrderRemainingQuantity(buyRemaining -sellRemaining);
                    order.setOrderRemainingQuantity(0);
                    fulfillManager.onOrderFulfilled(new FulfillOrder(order.getOrderId(), sellRemaining,
                            order.getOrderPrice(),buyOrder.getOrderId()));
                }else{
                    // only buy order extinguished
                    orderBook.pollBuy();
                    orderBook.removeOrderId(buyOrder.getOrderId());
                    order.setOrderRemainingQuantity(sellRemaining -buyRemaining);
                    fulfillManager.onOrderFulfilled(new FulfillOrder(order.getOrderId(), buyRemaining,
                            order.getOrderPrice(),buyOrder.getOrderId()));
                    // get new buy order
                    buyOrder = orderBook.peekBuy();


                }
            }
            if(order.getOrderRemainingQuantity() >0){// add sell to queue
                orderBook.offerSell(order);
                orderBook.addOrderId(order.getOrderId());
            }
        }
    }

    @Override
    public synchronized OrderBook getOrderBookForSymbol(String symbol) {
        OrderBook orderBook;
        if(!bookMap.containsKey(symbol)){
            orderBook = new OrderBook(symbol);
            bookMap.put(symbol, orderBook);
        }else {
            orderBook = bookMap.get(symbol);
        }
        return orderBook;
    }
}
