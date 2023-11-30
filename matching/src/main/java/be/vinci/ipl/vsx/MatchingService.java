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
  private final List<Order> buyOrders=new ArrayList<>();
  private final List<Order> sellOrders=new ArrayList<>();

  public MatchingService(PriceProxy priceProxy, ExecuteProxy executeProxy) {
    this.priceProxy = priceProxy;
    this.executeProxy = executeProxy;
  }

  /**
   * Exécute un nouvel ordre s'il n'existe pas déjà dans les listes d'ordres
   * et trouve une correspondance pour effectuer la transaction, si possible.
   *
   * @param order L'ordre à exécuter
   * @return true si l'ordre est exécuté avec succès, sinon false
   */
  public Boolean executeOrder(Order order){
    if(buyOrders.contains(order) || sellOrders.contains(order)) {
      return false;
    }
    Transaction transaction = matchOrder(order);
    if(transaction != null) {
      executeProxy.executeTransaction(transaction.getTicker(), transaction.getSeller(), transaction.getBuyer(), transaction);
    }

    return true;
  }

  /**
   * Trouve une correspondance pour l'ordre donné et génère une transaction
   * si une correspondance est trouvée, en mettant à jour les informations nécessaires.
   *
   * @param order L'ordre pour lequel trouver une correspondance
   * @return La transaction générée ou null si aucune correspondance n'est trouvée
   */
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

          if(sellOrder.getFilled()==sellOrder.getQuantity())
            sellOrders.remove(sellOrder);
          if(order.getFilled()< order.getQuantity())
            buyOrders.add(order);
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
          if(buyOrder.getQuantity()== buyOrder.getFilled())
            buyOrders.remove(buyOrder);
          if(order.getFilled()< order.getQuantity())
            sellOrders.add(order);
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

  /**
   * Vérifie si deux ordres correspondent et met à jour les détails de la transaction le cas échéant.
   *
   * @param actualOrder   L'ordre en cours de vérification
   * @param orderToMatch  L'ordre à vérifier si correspondant à l'ordre actuel
   * @param transaction   La transaction à mettre à jour si une correspondance est trouvée
   * @return true si une correspondance est trouvée, sinon false
   */
  private boolean isMatch(Order actualOrder, Order orderToMatch, Transaction transaction) {

    if(!actualOrder.getTicker().equals(orderToMatch.getTicker()))return false;
    Order buyOrder=null;
    Order sellOrder=null;
    if(actualOrder.getSide().equals(OrderSide.BUY)){
      buyOrder=actualOrder;
      sellOrder=orderToMatch;
    }else{
      buyOrder=orderToMatch;
      sellOrder=actualOrder;
    }

    int qntS=sellOrder.getQuantity()- sellOrder.getFilled();
    int qntB=buyOrder.getQuantity()-buyOrder.getFilled();

    if(buyOrder.getType().equals(OrderType.LIMIT)){
      //LIMIT et BUY
      if(sellOrder.getType().equals(OrderType.LIMIT)){
        //LIMIT et SELL

        if((sellOrder.getLimit().intValue() <= buyOrder.getLimit().intValue())){
          if(sellOrder.getFilled()+qntB<= sellOrder.getQuantity()){
            sellOrder.setFilled(sellOrder.getFilled()+qntB);
            buyOrder.setFilled(buyOrder.getQuantity());
            transaction.setQuantity(qntB);
          }else{
            buyOrder.setFilled(buyOrder.getFilled()+qntS);
            sellOrder.setFilled(sellOrder.getQuantity());
            transaction.setQuantity(qntS);
          }

          Number soustraction=buyOrder.getLimit().doubleValue()-sellOrder.getLimit().doubleValue();
          Number price=sellOrder.getLimit().doubleValue()+(soustraction.doubleValue()/2);
          transaction.setPrice(price);
          return true;
        }else{
          return false;
        }
      } else{
        //MARKET et SELL
        if(sellOrder.getFilled()+qntB<= sellOrder.getQuantity()){
          sellOrder.setFilled(sellOrder.getFilled()+qntB);
          buyOrder.setFilled(buyOrder.getQuantity());
          transaction.setQuantity(qntB);
        }else{
          buyOrder.setFilled(buyOrder.getFilled()+qntS);
          sellOrder.setFilled(sellOrder.getQuantity());
          transaction.setQuantity(qntS);
        }

        transaction.setPrice(buyOrder.getLimit());
        return true;

      }
    }else {
      //MARKET et BUY
      if(sellOrder.getType().equals(OrderType.LIMIT)){
        //LIMIT et SELL
        if(sellOrder.getFilled()+qntB<= sellOrder.getQuantity()){
          sellOrder.setFilled(sellOrder.getFilled()+qntB);
          buyOrder.setFilled(buyOrder.getQuantity());
          transaction.setQuantity(qntB);
        }else{
          buyOrder.setFilled(buyOrder.getFilled()+qntS);
          sellOrder.setFilled(sellOrder.getQuantity());
          transaction.setQuantity(qntS);
        }
        transaction.setPrice(sellOrder.getLimit());
        return true;
      } else{
        //MARKET et SELL je pense avoir besoin du price
        if(sellOrder.getFilled()+qntB<= sellOrder.getQuantity()){
          sellOrder.setFilled(sellOrder.getFilled()+qntB);
          buyOrder.setFilled(buyOrder.getQuantity());
          transaction.setQuantity(qntB);
        }else{
          buyOrder.setFilled(buyOrder.getFilled()+qntS);
          sellOrder.setFilled(sellOrder.getQuantity());
          transaction.setQuantity(qntS);
        }
        transaction.setPrice(priceProxy.getLastPrice(buyOrder.getTicker()));
        return true;
      }
    }
  }


}