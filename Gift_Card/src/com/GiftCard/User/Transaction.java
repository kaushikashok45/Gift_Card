package com.GiftCard.User;

import java.util.*;
import java.lang.String;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import com.GiftCard.Administration.*;
import com.GiftCard.TxtFileIO.ProductReader;
import com.GiftCard.TxtFileIO.TransactionReader;
import com.GiftCard.Connectors.*;

public class Transaction{
  private final String transactionID;
  private final String custID;
  private final String giftID;
  private final BigDecimal billAmount;
  private final String timestamp;
  private final char transaxType;
  private HashMap<String,Integer> productList;

  public Transaction(String prodIDs,String custID,String giftID) throws Exception{
    this.giftID=giftID;
    UUID uuid=UUID.randomUUID();
    this.custID=custID;
    String[] prods=prodIDs.split(",");
    BigDecimal total=new BigDecimal("0.0");
    productList=new HashMap<String,Integer>();
    for(String s:prods){
      s=s.trim();
      String[] prodMap=s.split("-");
      if(productList.containsKey(prodMap[0])){
        productList.replace(prodMap[0],(productList.get(prodMap[0])+(Integer.parseInt(prodMap[1]))));
      }
      else{
        productList.put(prodMap[0],(Integer.parseInt(prodMap[1])));
      }
      ProductReader temp=new ProductReader();
      total=total.add(((temp.getProduct(prodMap[0])).getAmount()).multiply(new BigDecimal(prodMap[1])));
    }
    billAmount=total;
    transactionID=uuid.toString();
    Date date=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    timestamp=formatter.format(date);
    transaxType='d';
  }

  Transaction(BigDecimal amount,String custID,String giftID) throws Exception{
    this.giftID=giftID;
    UUID uuid=UUID.randomUUID();
    this.custID=custID;
    billAmount=amount;
    transactionID=uuid.toString();
    productList=new HashMap<String,Integer>();
    productList.put("R",1);
    Date date=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    timestamp=formatter.format(date);
    transaxType='c';
  }

  public Transaction(String transaxID,String custID,String giftID,String billAmount,String timestamp,String transaxType,String transaxmap) throws Exception{
    this.transactionID=transaxID;
    this.custID=custID;
    this.giftID=giftID;
    this.billAmount=new BigDecimal(billAmount);
    this.timestamp=timestamp;
    this.transaxType=transaxType.charAt(0);
    this.productList=new HashMap<String,Integer>();
    String[] qty=transaxmap.split("and");
    for(int i=0;i<qty.length;i++){
      String[] values=qty[i].split(":",2);
      this.productList.put(values[0],(Integer.parseInt(values[1])));
    }
  }

  public void printTransaction() throws Exception{
    if(this.transaxType=='d'){
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Transaction "+this.transactionID+" details");
      System.out.println("Transaction id : "+this.transactionID);
      System.out.println("Customer id : "+this.custID);
      System.out.println("Gift-card id : "+this.giftID);
      System.out.println("Transaction type : purchase");
      System.out.println("Transaction timestamp : "+this.timestamp);
      System.out.println("Product id                "+"Product name            "+"Product price/unit"+"                   Quantity"+"            Product(s) price");
      for(String prodID:(this.productList.keySet())){
        Product p=(new ProductReader()).getProduct(prodID);
        System.out.println(p.getPid()+"                            "+p.getName()+"                     "+p.getAmount()+"                                "+productList.get(prodID)+"                   "+(p.getAmount()).multiply(BigDecimal.valueOf(productList.get(prodID))));
      }
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Total bill amount : "+this.billAmount);
      System.out.println("-------------------------------------------------------------------");
    }
    else{
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Transaction "+this.transactionID+" details");
      System.out.println("Transaction id : "+this.transactionID);
      System.out.println("Customer id : "+this.custID);
      System.out.println("Gift-card id : "+this.giftID);
      System.out.println("Transaction type : top-up");
      System.out.println("Transaction timestamp : "+this.timestamp);
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Card Top-up amount : "+this.billAmount);
      System.out.println("-------------------------------------------------------------------");
    }
  }

  public void printGoods() throws Exception{
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Product id                "+"Product name            "+"Product price/unit"+"                   Quantity"+"            Product(s) price");
      for(String prodID:(this.productList.keySet())){
        Product p=(new ProductReader()).getProduct(prodID);
        System.out.println(p.getPid()+"                            "+p.getName()+"                     "+p.getAmount()+"                                "+productList.get(prodID)+"                   "+(p.getAmount()).multiply(BigDecimal.valueOf(productList.get(prodID))));
      }
      System.out.println("-------------------------------------------------------------------");
      System.out.println("Total bill amount : "+this.billAmount);
      System.out.println("-------------------------------------------------------------------");
  }



  public BigDecimal getBillAmount(){
    return this.billAmount;
  }

  public String getTid(){
    return this.transactionID;
  }

  public char getTransaxType(){
    return this.transaxType;
  }

  public HashMap<String,Integer> getItems(){
    return this.productList;
  }

  public String getCustId(){
    return this.custID;
  }

  public String getGiftId(){
    return this.giftID;
  }

  public String getTimeStamp(){
    return this.timestamp;
  }

  public static void main(String[] args) throws Exception{
    Saver data=new ConnectTxt();
    Transaction t1=new Transaction("1-2,2-3,2-4","A123","abcde");
    BigDecimal top=new BigDecimal("1000.35");
    Transaction t2=new Transaction(top,"B124","abcde");
    TransactionReader tr=new TransactionReader();
    t1.printTransaction();
    t2.printTransaction();
    (data).save(t1);
    tr.printTransactions();
  }

}


