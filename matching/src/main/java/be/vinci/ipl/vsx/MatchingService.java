package be.vinci.ipl.vsx;

import be.vinci.ipl.vsx.models.Order;
import be.vinci.ipl.vsx.models.Order.OrderSide;
import be.vinci.ipl.vsx.models.Order.OrderType;
import be.vinci.ipl.vsx.models.Transaction;
import be.vinci.ipl.vsx.repositories.ExecuteProxy;
import be.vinci.ipl.vsx.repositories.PriceProxy;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

  private final PriceProxy priceProxy;
  private final ExecuteProxy executeProxy;

  public MatchingService(PriceProxy priceProxy, ExecuteProxy executeProxy) {
    this.priceProxy = priceProxy;
    this.executeProxy = executeProxy;
  }

  private List<Order> buyOrders=new ArrayList<>();
  private List<Order> sellOrders=new ArrayList<>();


  public Boolean excuteOrder(Order order){
    if(buyOrders.contains(order)||sellOrders.contains(order))return false;
    Transaction transaction=matchOrder(order);
    if(transaction!=null)executeProxy.executeTransaction(transaction.getTicker(),transaction.getSeller(),transaction.getBuyer(),transaction);
    return true;
  }

  private Transaction matchOrder(Order order) {
    boolean match=false;
    Transaction matchedOrderTransaction=new Transaction();
   if(order.getSide().equals(OrderSide.BUY)){
     for (Order sellOrder:sellOrders) {
       match=isMatch(sellOrder,order,matchedOrderTransaction);
       if(match){
         matchedOrderTransaction.setSeller(sellOrder.getOwner());
         matchedOrderTransaction.setBuyer(order.getOwner());
         matchedOrderTransaction.setBuy_order_guid(order.getGuid());
         matchedOrderTransaction.setSell_order_guid(sellOrder.getGuid());
         matchedOrderTransaction.setTicker(sellOrder.getTicker());

         if(sellOrder.getQuantity()==0)
         sellOrders.remove(sellOrder);
         break;
       }
     }
   }else{
     for (Order buyOrder:buyOrders) {
       match=isMatch(buyOrder,order,matchedOrderTransaction);
       if (match){
         matchedOrderTransaction.setSeller(order.getOwner());
         matchedOrderTransaction.setBuyer(buyOrder.getOwner());
         matchedOrderTransaction.setBuy_order_guid(buyOrder.getGuid());
         matchedOrderTransaction.setSell_order_guid(order.getGuid());
         matchedOrderTransaction.setTicker(buyOrder.getTicker());
         buyOrders.remove(buyOrder);
         break;
       }
     }
   }
    if (!match) {
      if (order.getSide().equals(OrderSide.BUY)) {
        buyOrders.add(order);
      } else {
        sellOrders.add(order);
      }
      return null;
    }
    return matchedOrderTransaction;
  }

  private boolean isMatch(Order actualOrder, Order orderToMatch, Transaction transaction) {

    if(actualOrder.getTicker()!=orderToMatch.getTicker())return false;
    Order buyOrder=null;
    Order sellOrder=null;
    if(actualOrder.getSide().equals(OrderSide.BUY)){
      buyOrder=actualOrder;
      sellOrder=orderToMatch;
    }else{
      buyOrder=orderToMatch;
      sellOrder=actualOrder;
    }

    int qnt=sellOrder.getQuantity()-buyOrder.getQuantity();
    if(qnt<0)return false;

      if(buyOrder.getType().equals(OrderType.LIMIT)){
        //LIMIT et BUY
        if(sellOrder.getType().equals(OrderType.LIMIT)){
          //LIMIT et SELL

          if((sellOrder.getLimit().intValue() <= buyOrder.getLimit().intValue())){
            sellOrder.setQuantity(qnt);
            transaction.setQuantity(buyOrder.getQuantity());
            Number soustraction=buyOrder.getLimit().doubleValue()-sellOrder.getLimit().doubleValue();
            Number price=sellOrder.getLimit().doubleValue()+(soustraction.doubleValue()/2);
            transaction.setPrice(price);
            return true;
          }
        } else{
          //MARKET et SELL
          sellOrder.setQuantity(qnt);
          transaction.setQuantity(buyOrder.getQuantity());
          transaction.setPrice(buyOrder.getLimit());
          return true;

        }
      }else {
        //MARKET et BUY
        if(sellOrder.getType().equals(OrderType.LIMIT)){
          //LIMIT et SELL
          sellOrder.setQuantity(qnt);
          transaction.setQuantity(buyOrder.getQuantity());
          transaction.setPrice(sellOrder.getLimit());
          return true;
        } else{
          //MARKET et SELL je pense avoir besoin du price
          sellOrder.setQuantity(qnt);
          transaction.setQuantity(buyOrder.getQuantity());
          transaction.setPrice(priceProxy.getLastPrice(buyOrder.getTicker()));
          return true;
        }
      }

    return false;
  }


}
