package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import com.GiftCard.Administration.*;

public class ProductReader {
  public static final String ANSI_RESET ="\u001B[0m";
  public static final String ANSI_YELLOW ="\u001B[33m";
  public static final String ANSI_RED ="\u001B[41m";
  public static final String ANSI_GREEN ="\u001B[42m";


  public ArrayList<String> readProductStrings(){
   ArrayList<String> stringsOfProducts=new ArrayList<String>();
   File dir=new File("./../res/");
   File file=new File(dir,"Products.txt");
   if((!(file.exists()))&&((file.isDirectory()))){
     System.out.println("No products added yet...Please try again later!");
   }
   try(FileReader reader=new FileReader(file);BufferedReader buffReader=new BufferedReader(reader)){
     String product;
     while((product=(buffReader.readLine()))!=null){
       stringsOfProducts.add(product);
     }
   }
   catch(IOException e){
     System.out.println("Error retrieving data from file!");
     e.printStackTrace();
   }
   return stringsOfProducts;
 }

  public ArrayList<Product> readProducts() throws IOException{
    ArrayList<String> productStrings=this.readProductStrings();
    ArrayList<Product> products=new ArrayList<Product>();
    for(String s:productStrings){
      String[] details=s.split(",",6);
      Product p=new Product(details[0],details[1],details[2],details[3],details[4],details[5]);
      products.add(p);
    }
    return products;
  }

  public void updateStock(String pid,int qty){
    ArrayList<String> productStrings=this.readProductStrings();
    ArrayList<String> temp=new ArrayList<String>();
    for(String s:productStrings){
      String[] details=s.split(",",6);
      if(pid.equals(details[2])){
        details[3]=Integer.toString(qty);
      }
      s=String.join(",",details);
      temp.add(s);
    }
    try{
       File dir=new File("./../res/");
       File file=new File(dir,"Products.txt");
      new FileWriter(file).close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    for(String s:temp){
       File dir=new File("./../res/");
      File file=new File(dir,"Products.txt");
      try( FileWriter writer=new FileWriter(file,true)){
        writer.write(s);
        writer.write("\n");
        writer.flush();
      }
      catch(IOException e){
        System.out.println("Error while updating product stocks!");
        e.printStackTrace();
      }
    }
  }

  public void updateStockStatus(String pid,String status){
    ArrayList<String> productStrings=this.readProductStrings();
    ArrayList<String> temp=new ArrayList<String>();
    for(String s:productStrings){
      String[] details=s.split(",",6);
      if(pid.equals(details[2])){
        details[4]=status;
      }
      s=String.join(",",details);
      temp.add(s);
    }
    try{
       File dir=new File("./../res/");
       File file=new File(dir,"Products.txt");
       new FileWriter(file).close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    for(String s:temp){
      File dir=new File("./../res/");
      File file=new File(dir,"Products.txt");
      try(FileWriter writer=new FileWriter(file,true)){
        writer.write(s);
        writer.write("\n");
        writer.flush();
      }
      catch(IOException e){
        System.out.println("Error while updating product stocks!");
        e.printStackTrace();
      }
    }
  }

  public void updatePrice(String pid,String price){
    ArrayList<String> productStrings=this.readProductStrings();
    ArrayList<String> temp=new ArrayList<String>();
    for(String s:productStrings){
      String[] details=s.split(",",6);
      if(pid.equals(details[2])){
        details[1]=price;
      }
      s=String.join(",",details);
      temp.add(s);
    }
    try{
      File dir=new File("./../res/");
      File file=new File(dir,"Products.txt");
      new FileWriter(file).close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    for(String s:temp){
      File dir=new File("./../res/");
      File file=new File(dir,"Products.txt");
      try(FileWriter writer=new FileWriter(file,true)){
        writer.write(s);
        writer.write("\n");
        writer.flush();
      }
      catch(IOException e){
        System.out.println("Error while updating product stocks!");
        e.printStackTrace();
      }
    }
  }

  public void updateAddedBy(String pid){
    ArrayList<String> productStrings=this.readProductStrings();
    ArrayList<String> temp=new ArrayList<String>();
    for(String s:productStrings){
      String[] details=s.split(",",6);
      if(pid.equals(details[2])){
        details[5]=pid;
      }
      s=String.join(",",details);
      temp.add(s);
    }
    try{
      File dir=new File("./../res/");
      File file=new File(dir,"Products.txt");
      new FileWriter(file).close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    for(String s:temp){
      File dir=new File("./../res/");
      File file=new File(dir,"Products.txt");
      try(FileWriter writer=new FileWriter(file,true)){
        writer.write(s);
        writer.write("\n");
        writer.flush();
      }
      catch(IOException e){
        System.out.println("Error while updating product stockss!");
        e.printStackTrace();
      }
    }
  }

  public void deleteStock(String pid){
    ArrayList<String> productStrings=this.readProductStrings();
    ArrayList<String> temp=new ArrayList<String>();
    for(String s:productStrings){
      String[] details=s.split(",",5);
      if(pid.equals(details[2])){
       continue;
      }
      s=String.join(",",details);
      temp.add(s);
    }
    try{
      File dir=new File("./../res/");
      File file=new File(dir,"Products.txt");
      new FileWriter(file).close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    for(String s:temp){
      File dir=new File("./../res/");
      File file=new File(dir,"Products.txt");
      try(FileWriter writer=new FileWriter(file,true)){
        writer.write(s);
        writer.write("\n");
        writer.flush();
      }
      catch(IOException e){
        System.out.println("Error while updating product stocks!");
        e.printStackTrace();
      }
    }
  }

  public Product getProduct(String pid) throws IOException{
    ArrayList<Product> prdcts=this.readProducts();
    Product result=null;
    for(Product p:prdcts){
      if(pid.equals(p.getPid())){
        result=p;
        break;
      }
    }
    return result;
  }

  public Product getLastProduct() throws IOException{
    ArrayList<Product> prdcts=this.readProducts();
    Product result=null;
    for(Product p:prdcts){
        result=p;
    }
    return result;
  }



  public void printProducts() throws IOException{
    ArrayList<Product> listProducts=this.readProducts();
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

  public int countProducts() throws IOException{

    int count=0;
    boolean fileCheck=false;
    File dir=new File("./../res/");
    File file=new File(dir,"Products.txt");
    if(file.exists() && !(file.isDirectory())){
        fileCheck=true;
    }
    System.out.println(fileCheck);
    if(fileCheck){
      ArrayList<Product> prods=this.readProducts();
      count=prods.size();
    }
    return count;
  }

  public boolean checkProductExists(String name,String amount) throws IOException{
    boolean result=false;
    BigDecimal price=new BigDecimal(amount);
    File dir=new File("./../res/");
    File file=new File(dir,"Products.txt");
    if(file.exists() && !(file.isDirectory())){
      ArrayList<Product> listProducts=this.readProducts();
      for(Product p:listProducts){
        if((name.equals(p.getName()))&&(price.equals(p.getAmount()))){
          result=true;
          break;
        }
      }
    }
    return result;
  }

  public boolean checkProductExists(String pid) throws IOException{
    boolean result=false;
    File dir=new File("./../res/");
    File file=new File(dir,"Products.txt");
    if(file.exists() && !(file.isDirectory())){
      ArrayList<Product> listProducts=this.readProducts();
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