package com.GiftCard.Administration;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import com.GiftCard.TxtFileIO.*;

public class Product{
  private String name;
  private BigDecimal amount;
  private String pid;
  private int qty;
  private String status;
  private String addedBy;

    public Product(String name,String amount,String qty,String aid) throws IOException{
    ProductReader temp=new ProductReader();
    if(!temp.checkProductExists(name,amount)){
      name=name.replaceAll(","," ");
      this.name=name;
      this.amount=new BigDecimal(amount);
      this.pid=Integer.toString(temp.countProducts());
      if(temp.countProducts()>0){
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

  public void saveProduct() throws IOException{
    File dir=new File("./../res/");
    File file=new File(dir,"Products.txt");
    try(FileWriter writer=new FileWriter(file,true)){
      String currProduct=this.name+","+this.amount+","+this.pid+","+this.qty+","+this.status+","+this.addedBy;
      writer.write(currProduct);
      writer.write("\n");
      writer.flush();
      System.out.println("Product saved successfully!");
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }

  public void updateStock(String qty) throws IOException{
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

  public void addStock(String qty) throws IOException{
    int inStock=Integer.parseInt(qty);
    this.qty=(this.qty)+inStock;
    ProductReader pr=new ProductReader();
    if(this.qty>0){
      this.status="Available";
      pr.updateStock(this.getPid(),this.getQty());
      pr.updateStockStatus(this.getPid(),this.getStatus());
      System.out.println("Stock added successfully!");
    }
  }

  public void changePrice() throws IOException{
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

  public void changeAddedBy(String aid){
    this.addedBy=aid;
    ProductReader pr=new ProductReader();
    pr.updateAddedBy(this.getPid());
  }



  public static void main(String[] args) throws IOException{
    char check='Y';
    while(check=='Y'){
      Scanner sc=new Scanner(System.in);
      System.out.println("Enter product name : ");
      String name=sc.nextLine();
      boolean flagp=true;
      String price=null;
      while(flagp){
        System.out.println("Enter product price: ");
        price=sc.nextLine();
        try{
          float a=Float.parseFloat(price);
          flagp=false;
        }
        catch(NumberFormatException e){
          System.out.println("Please enter a valid price!");
        }
      }
      boolean flag=true;
      String qty=null;
      while(flag){
        System.out.println("Enter product quantity: ");
        qty=sc.nextLine();
        try{
          int a=Integer.parseInt(qty);
          flag=false;
        }
        catch(NumberFormatException e){
          System.out.println("Please enter a valid quantity!");
        }
      }
      Product p=new Product(name,price,qty,"aadmin123");
      if((p.getName())!=null){
        p.saveProduct();
      }
      else{
        System.out.println("Product already added!");
      }
      System.out.println("Do you want to add more products?(Y/N): ");
      check=sc.next().charAt(0);
    }
   
  }

}




