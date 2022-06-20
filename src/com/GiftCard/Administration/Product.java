package com.GiftCard.Administration;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.Connectors.*;

public class Product{
  private String name;
  private BigDecimal amount;
  private String pid;
  private int qty;
  private String status;
  private String addedBy;
   
    public Product(String name,String amount,String qty,String aid) throws Exception{
      
    ProductReader temp=new ProductReader();
    Saver data=new ConnectSQL();
    if(!temp.checkProductExists(name,amount)){
      name=name.replaceAll(","," ");
      this.name=name;
      this.amount=new BigDecimal(amount);
      this.pid=Integer.toString((data).counter("Products.txt"));
      if((data).counter("Products.txt")>0){
        int stemp=(Integer.parseInt((temp.getLastProduct()).getPid()))+1;
        this.pid=Integer.toString(stemp);
      }
      this.qty=Integer.parseInt(qty);
      this.addedBy=aid;
      if((this.qty)<0){
        this.qty=0;
      }
      if((this.qty)>0){
        this.status="Available";
      }
      else{
        this.status="Out of stock";
      }
    }
  }

    public Product(String name,String amount,String pid,String qty,String stat,String admin){
    this.name=name;
    this.amount=new BigDecimal(amount);
    this.pid=pid;
    this.qty=Integer.parseInt(qty);
    this.status=stat;
    this.addedBy=admin;
  }

  public String getName(){
    return this.name;
  }

  public BigDecimal getAmount(){
    return this.amount;
  }

  public String getPid(){
    return this.pid;
  }

  public int getQty(){
    return this.qty;
  }

  public String getStatus(){
    return this.status;
  }

  public String getAddedBy(){
    return this.addedBy;
  }  

 

  public void updateStock(String qty) throws Exception{
    int bought=Integer.parseInt(qty);
    this.qty=(this.qty)-bought;
    ProductReader pr=new ProductReader();
    if(this.qty<=0){
      this.status="Out of stock";
      pr.updateStock(this.getPid(),this.getQty());
      pr.updateStockStatus(this.getPid(),this.getStatus());
    }
    else{
        pr.updateStock(this.getPid(),this.getQty());
    }
  }

  public void addStock(String qty,String aid) throws Exception{
    int inStock=Integer.parseInt(qty);
    this.qty=(this.qty)+inStock;
    ProductReader pr=new ProductReader();
    if(this.qty>0){
      this.status="Available";
      pr.updateStock(this.getPid(),this.getQty());
      pr.updateStockStatus(this.getPid(),this.getStatus());
      this.changeAddedBy(aid);
      System.out.println("Stock added successfully!");
    }
  }

  public void changePrice() throws Exception{
    Scanner sc=new Scanner(System.in);
    System.out.println("");
    boolean flag=true;
    String price=null;
    ProductReader pr=new ProductReader();
    while(flag){
      System.out.print("Enter new price for product "+this.getPid()+" : ₹");
      price=sc.nextLine();
      System.out.println("");
      try{
          float f=Float.parseFloat(price);
          flag=false;
      }
      catch(NumberFormatException e){
        System.out.println("Enter a valid price!");
      }
    }
    pr.updatePrice(this.getPid(),price);
    System.out.println("Product price updated successfully!");
  
  }

  public void changeAddedBy(String aid) throws Exception{
    this.addedBy=aid;
    ProductReader pr=new ProductReader();
    pr.updateAddedBy(this.getPid(),aid);
  }



  public static void main(String[] args) throws Exception{
     Product p=new Product("KDE","1000","10","adminno");
     Saver s=new ConnectSQL();
     s.save(p);
  }

}




