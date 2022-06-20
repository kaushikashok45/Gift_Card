package com.GiftCard.User;

import java.util.*;
import java.lang.String;
import java.io.*;
import java.math.BigDecimal;
import com.GiftCard.Administration.*;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.Connectors.*;

public class GiftCard{
  private final String cardno;
  private String custID;
  private String pin;
  private BigDecimal balance;

  public void clearConsoleScreen() throws Exception {
        System.out.print("\033[H\033[2J");
        System.out.flush();
  }

  public static boolean isNumeric(String s){
    int len=s.length();
    boolean check=true;
    for(int i=0;i<len;i++){
      try{
        int temp=Integer.parseInt((Character.toString(s.charAt(i))));
      }
      catch(NumberFormatException e){
        check=false;
        break;
      }
    }
    return check;
  }

   public GiftCard(String custID) throws Exception{
    clearConsoleScreen();
    System.out.println("------------------------");
    System.out.println("       Buy Gift-Card");
    System.out.println("------------------------");
    System.out.println("");
    Scanner sc=new Scanner(System.in);
    this.cardno=String.format("%08d",(((new GiftCardReader()).getLastGiftCard())+1));
    System.out.println("Allocated card number : "+this.cardno);
    this.custID=custID;
    String pin1="";
    String pin2=null;
    boolean flag=false;
    Console cnsl=null;
    do{
      System.out.println("Enter pin for card: ");
      pin1=sc.nextLine();
      if(!(isNumeric(pin1))){
        System.out.println("Please use only numbers for PIN!");
        pin1="";
        continue;
      }
      else if(!pin1.matches("\\S+")){
        pin1="\\";
        continue;
      }
      else if(pin1.length()<4){
        System.out.println("Enter a 4 digit number!");
        pin1="";
        continue;
      }
      else if(pin1.length()>4){
        System.out.println("Max 4 digits allowed in pin!");
        pin1="";
        continue;
      }
      System.out.println("Re-enter pin for card: ");
      pin2=sc.nextLine();
      if(!(pin1.equals(pin2))){
        System.out.println("PINs do not match!");
      }
    }while((!(pin1.equals(pin2))));
    this.pin=pin1;
    this.balance=new BigDecimal("0.00");
    System.out.println("Gift-card created successfully!");
    GiftCardReader gr=new GiftCardReader();
    Saver data=new ConnectSQL();
    (data).save(this);
    this.printGiftCard();
   
  }

  public GiftCard(String cardID,String custID,String bal,String pin) throws Exception{
    this.cardno=cardID;
    this.custID=custID;
    this.setBalance(new BigDecimal(bal));
    this.pin=pin;
  }

  public void topUp(String amount) throws Exception{
    boolean check=false;
    try{
      this.setBalance(((this.balance).add((new BigDecimal(amount)))));
      check=true;
    }
    catch(Exception e){
      System.out.println("Top-up failed!");
    }
    if(check){
      Transaction temp=new Transaction(new BigDecimal(amount),this.getCustId(),this.getGiftCardId());
      TransactionReader tr=new TransactionReader();
      Saver data=new ConnectSQL();
      (data).save(temp);
      GiftCardReader g=new GiftCardReader();
      g.updateBalance(this.getGiftCardId(),this.getBalance());
    }
  }

  public boolean purchase(String prodQtyMap,String billAmt) throws Exception{
    boolean result=false;
    try{
      this.balance=(this.balance).subtract(new BigDecimal(billAmt));
      Transaction temp=new Transaction(prodQtyMap,this.getCustId(),this.getGiftCardId());
      TransactionReader tr=new TransactionReader();
      Saver data=new ConnectSQL();
      (data).save(temp);
      result=true;
      for(String s:(temp.getItems()).keySet()){
        Product p=(new ProductReader()).getProduct(s);
        p.updateStock(Integer.toString((temp.getItems()).get(s)));
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return result;
  }

  public void showTransaction() throws Exception{
    TransactionReader tr=new TransactionReader();
    ArrayList<Transaction> transactions=tr.getGiftTransax(this.getGiftCardId());
    System.out.println("Transactions made using gift card number: "+this.getGiftCardId());
    for(Transaction t:transactions){
      t.printTransaction();
    }
  }



   public void printGiftCard(){
     System.out.println("");
     System.out.println("--------------------------------------------");
     System.out.println("Gift card "+this.getGiftCardId()+" details");
     System.out.println("Gift card ID: "+this.getGiftCardId());
     System.out.println("Customer ID: "+this.getCustId());
     System.out.println("Card balance: ₹"+this.getBalance());
     System.out.println("--------------------------------------------");
   }

   public String getGiftCardId(){
     return this.cardno;
   }

   public String getCustId(){
     return this.custID;
   }

   public String getPin(){
     return this.pin;
   }

   public BigDecimal getBalance(){
     return this.balance;
   }

   public void setBalance(BigDecimal bal){
     this.balance=bal;
   }

   public static void main(String[] args) throws Exception{
     GiftCard g1=new GiftCard("000000001");
     Saver s=new ConnectSQL();
     s.save(g1);
   }
 }


