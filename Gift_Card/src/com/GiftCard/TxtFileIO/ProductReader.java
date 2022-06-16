package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import com.GiftCard.Administration.*;
import com.GiftCard.Connectors.*;

public class ProductReader extends ConnectTxt{
  public static final String ANSI_RESET ="\u001B[0m";
  public static final String ANSI_YELLOW ="\u001B[33m";
  public static final String ANSI_RED ="\u001B[41m";
  public static final String ANSI_GREEN ="\u001B[42m";

  Saver data=new ConnectTxt();

  public void updateStock(String pid,int qty) throws Exception{
    String val=Integer.toString(qty);
    data.update("stock",val,pid,"Products.txt");
  }

  public void updateStockStatus(String pid,String status) throws Exception{
    data.update("status",status,pid,"Products.txt");
  }

  public void updatePrice(String pid,String price) throws Exception{
    data.update("price",price,pid,"Products.txt");
  }

  public void updateAddedBy(String pid,String aid) throws Exception{
    data.update("addedBy",aid,pid,"Products.txt");
  }

  public void deleteStock(String pid) throws Exception{
    data.remove(pid,"Products.txt");
  }

  public Product getProduct(String pid) throws Exception{
    ArrayList<Product> prdcts=(data).readProducts("Products.txt");
    Product result=null;
    for(Product p:prdcts){
      if(pid.equals(p.getPid())){
        result=p;
        break;
      }
    }
    return result;
  }

  public Product getLastProduct() throws Exception{
    ArrayList<Product> prdcts=(data).readProducts("Products.txt");
    Product result=null;
    for(Product p:prdcts){
        result=p;
    }
    return result;
  }



  public void printProducts() throws Exception{
    ArrayList<Product> listProducts=(data).readProducts("Products.txt");
    for(Product s:listProducts){
      if((s.getQty())>10){
        System.out.println("");
        System.out.print("Product id: "+s.getPid()+" | "+"Product name: "+s.getName()+" | "+"Product price: ₹"+s.getAmount());
        System.out.print(" | Stock availability : "+ANSI_GREEN+s.getStatus()+ANSI_RESET+" | "+"Stock last added by: "+s.getAddedBy());
        System.out.println("");
      }
      else if((s.getQty())==0){
        System.out.println("");
        System.out.print("Product id: "+s.getPid()+" | "+"Product name: "+s.getName()+" | "+"Product price: ₹"+s.getAmount());
        System.out.print(" | Stock availability : "+ANSI_RED+s.getStatus()+ANSI_RESET+"|"+"Stock last added by: "+s.getAddedBy());
        System.out.println("");
      }
      else{
        System.out.println("");
        System.out.print("Product id: "+s.getPid()+" | "+"Product name: "+s.getName()+" | "+"Product price: ₹"+s.getAmount());
        System.out.print(" | Stock availability : "+ANSI_GREEN+s.getStatus()+ANSI_RESET);
        System.out.print(" | Only "+ANSI_YELLOW+s.getQty()+" pieces left! "+ANSI_RESET+"| "+"Stock last added by: "+s.getAddedBy());
        System.out.println("");
      }
    }
  }



  public boolean checkProductExists(String name,String amount) throws Exception{
    boolean result=false;
    ArrayList<Product> listProducts=(data).readProducts("Products.txt");
    if(listProducts!=null){
      for(Product p:listProducts){
        if((name.equals(p.getName()))&&(amount.equals((p.getAmount()).toString()))){
          result=true;
          break;
        }
      }
    }
    return result;
  }

  public boolean checkProductExists(String pid) throws Exception{
    boolean result=false;
    ArrayList<Product> listProducts=(data).readProducts("Products.txt");
    if(listProducts!=null){
      for(Product p:listProducts){
        if(pid.equals(p.getPid())){
          result=true;
          break;
        }
      }
    }
    return result;
  }
}
