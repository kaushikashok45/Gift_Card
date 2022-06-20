package com.GiftCard.Purchase;


import java.util.*;
import java.lang.String;
import java.io.*;
import java.math.BigDecimal;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.User.*;

public class Purchase{

  public void clearConsoleScreen() throws Exception {
       System.out.print("\033[H\033[2J");
       System.out.flush();
  }

  public static boolean isNumeric(String s){
    s=s.trim();
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

  public static boolean isFloat(String s){
    s=s.trim();
    int len=s.length();
    boolean check=true;
    for(int i=0;i<len;i++){
      try{
        float temp=Float.parseFloat((Character.toString(s.charAt(i))));
      }
      catch(NumberFormatException e){
        check=false;
        break;
      }
    }
    return check;
  }

  public static boolean checkBalance(BigDecimal bal,BigDecimal bill){
    boolean result=false;
    int ans=bal.compareTo(bill);
    if((ans==1)||(ans==0)){
      result=true;
    }
    return result;
  }

  public void listProducts() throws Exception{
    clearConsoleScreen();
    System.out.println("<----Product List---->");
    ProductReader pr=new ProductReader();
    pr.printProducts();
    System.out.println("\n");
  }

  public String productsChooser() throws Exception{
    System.out.println("------------------------");
    System.out.println("    Product Selector");
    System.out.println("------------------------");
    Scanner sc=new Scanner(System.in);
    String result="";
    char ch='Y';
    while(ch=='Y'){
      String prodId,qty;
      System.out.println("Enter product ID: ");
      prodId=sc.nextLine();
      prodId=prodId.trim();
      System.out.println("Enter product quantity: ");
      qty=sc.nextLine();
      qty=qty.trim();
      if((!((new ProductReader()).checkProductExists(prodId)))||((!(isNumeric(qty)))&&(!((Integer.parseInt(qty))>0))&&(!(isFloat(qty))))){
        System.out.println("Invalid product details....Try again!");
        continue;
      }
      else if((((new ProductReader()).getProduct(prodId)).getQty())<=0){
        System.out.println("Product out of stock!");
        continue;
      }
      else if((((new ProductReader()).getProduct(prodId)).getQty())<(Integer.parseInt(qty))){
        System.out.println("Only "+(((new ProductReader()).getProduct(prodId)).getQty())+" pieces available!");
        System.out.println("Please try again with a smaller quantity!");
      }
      result=result+prodId+"-"+qty+",";
      System.out.println("Do you want to add more products(Y/N)? : ");
      ch=sc.next().charAt(0);
      sc.nextLine();
    }
    System.out.println("\n");
   
    return result;
  }

  public String getCardDetails() throws Exception{
    String result=null;
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter card number: ");
    String gid=sc.nextLine();
    if((new GiftCardReader()).checkGiftCardExists(gid)){
      result=gid;
    }
    else{
      System.out.println("Gift card does not exist!");
    }
    System.out.println("\n");
    return result;
  }

  public void makePurchase(String products,String gid) throws Exception{
    clearConsoleScreen();
    System.out.println("------------------------");
    System.out.println("   Order Checkout");
    System.out.println("------------------------");
    System.out.println("");
    Scanner sc=new Scanner(System.in);
    Transaction temp=new Transaction(products,((((new GiftCardReader()).getGiftCard(gid)).getCustId())),gid);
    temp.printGoods();
    System.out.println("Confirm Order(Y/N)? : ");
    char ch=sc.next().charAt(0);
    if(ch=='Y'){
      boolean verify=false;
      GiftCard g=((new GiftCardReader()).getGiftCard(gid));
      sc.nextLine();
      for(int i=0;i<3;i++){
        System.out.println("Enter PIN for card "+gid+" : ");
        String pin=sc.nextLine();
        if(pin.equals(g.getPin())){
          verify=true;
          break;
        }
        else{
          System.out.println("Incorrect PIN!");
        }
      }
      if((verify)&&(checkBalance(g.getBalance(),temp.getBillAmount()))){
        g.purchase(products,((temp.getBillAmount()).toString()));
        System.out.println("Purchase successful!");
        System.out.println("Available balance on card "+gid+" : "+((g.getBalance()).toString()));
      }
      else if(!verify){
        System.out.println("Multiple Incorrect PIN entry!");
        return;
      }
      else{
        System.out.println("Purchase failed due to insufficient balance in gift card!");
      }
    }
    else{
      System.out.println("Purchase cancelled!");
    }
    System.out.println("\n");
  }

  public static void main(String[] args) throws Exception{
    Purchase p=new Purchase();
    Scanner sc=new Scanner(System.in);
    p.listProducts();
    String prods=p.productsChooser();
    String gid=p.getCardDetails();
    p.makePurchase(prods,gid);
  }
}
