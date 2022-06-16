package com.GiftCard.Connectors;

import com.GiftCard.Administration.*;
import com.GiftCard.Purchase.*;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.User.*;
import java.io.*;
import java.util.*;
import java.math.BigDecimal;

 public class ConnectTxt implements Saver{

  public void save(){
    System.out.println("Saved!");
  }

   public void save(Product p) throws Exception{
     File dir=new File("./../res/");
     File file=new File(dir,"Products.txt");
     try(FileWriter writer=new FileWriter(file,true)){
       String currProduct=p.getName()+","+p.getAmount()+","+p.getPid()+","+p.getQty()+","+p.getStatus()+","+p.getAddedBy();
       writer.write(currProduct);
       writer.write("\n");
       writer.flush();
       System.out.println("Product saved successfully!");
     }
     catch(Exception e){
       e.printStackTrace();
     }
   }

   public void save(Transaction t) throws Exception{
     File dir=new File("./../res/");
     File file=new File(dir,"Transactions.txt");
     try(FileWriter writer=new FileWriter(file,true)){
       String currTransax=t.getTid()+","+t.getCustId()+","+t.getGiftId()+","+t.getBillAmount()+","+t.getTimeStamp()+","+t.getTransaxType()+",";
       String temp=null;
       int len=(t.getItems()).size();
       int count=0;
       for(String s:(t.getItems()).keySet()){
         if(temp!=null){
           temp=temp+s+":"+(t.getItems()).get(s)+"and";
         }
         else if(count==(len-1)){
           temp=s+":"+(t.getItems()).get(s);
         }
         else{
           temp=s+":"+(t.getItems()).get(s)+"and";
         }
         count+=1;
       }
       currTransax=currTransax+temp;
       writer.write(currTransax);
       writer.write("\n");
       writer.flush();
       System.out.println("Transaction saved successfully!");
     }
     catch(Exception e){
       e.printStackTrace();
     }
   }

   public void save(GiftCard g) throws Exception{
     File dir=new File("./../res/");
     File file=new File(dir,"Giftcards.txt");
     try(FileWriter writer=new FileWriter(file,true)){
       String currTransax=g.getGiftCardId()+","+g.getCustId()+","+g.getBalance()+","+g.getPin();
       writer.write(currTransax);
       writer.write("\n");
       writer.flush();
       System.out.println("Gift card saved successfully!");
     }
     catch(Exception e){
       System.out.println("Error while saving gift card!");
       e.printStackTrace();
     }
   }

   public void save(Customer c) throws Exception{
     File dir=new File("../res/");
     File file=new File(dir,"Customers.txt");
     try(FileWriter writer=new FileWriter(file,true)){
       String currTransax=c.getCustId()+","+c.getName()+","+c.getEmail()+","+c.getPassword();
       writer.write(currTransax);
       writer.write("\n");
       writer.flush();
       System.out.println("Customer account created successfully!");
     }
     catch(Exception e){
       System.out.println("Error while saving customer details!");
       e.printStackTrace();
     }
   }

   public void save(Admin a) throws Exception{
     File dir=new File("./../res/");
     File file=new File(dir,"Admin.txt");
     try(FileWriter writer=new FileWriter(file,true)){
       String currTransax=a.getAid()+","+a.getPassword()+","+a.getName()+","+a.getEmail();
       writer.write(currTransax);
       writer.write("\n");
       writer.flush();
       System.out.println("Admin account created successfully!");
     }
     catch(Exception e){
       System.out.println("Error while saving admin details!");
       e.printStackTrace();
     }
   }

   @Override
   public ArrayList<String> readStrings(String fileName){
     ArrayList<String> data=new ArrayList<String>();
     File dir=new File("./../res/");
     File file=new File(dir,fileName);
     if((!(file.exists()))&&((file.isDirectory()))){
       System.out.println("No data found!");
       return data;
     }
     try(FileReader reader=new FileReader(file);BufferedReader buffReader=new BufferedReader(reader)){
       String line;
       while((line=(buffReader.readLine()))!=null){
         data.add(line);
       }
     }
     catch(Exception e){
       System.out.println("Error retrieving data from file!");
     }
     return data;
   }

   @Override
   public ArrayList<Object> readObjects(String fileName) throws Exception{
     ArrayList<Object> objects=new ArrayList<Object>();
     ArrayList<String> data=readStrings(fileName);
     if(fileName.equals("Products.txt")){
       for(String s:data){
         String[] details=s.split(",",6);
         Product p=new Product(details[0],details[1],details[2],details[3],details[4],details[5]);
         objects.add(p);
       }
     }
     else if(fileName.equals("Transactions.txt")){
       for(String s:data){
         String[] details=s.split(",");
         Object p=new Transaction(details[0],details[1],details[2],details[3],details[4],details[5],details[6]);
         objects.add(p);
       }
     }
     else if(fileName.equals("Giftcards.txt")){
       for(String s:data){
         String[] details=s.split(",",4);
         Object p=new GiftCard(details[0],details[1],details[2],details[3]);
         objects.add(p);
       }
     }
     else if(fileName.equals("Customers.txt")){
       for(String s:data){
         String[] details=s.split(",",4);
         Object p=new Customer(details[0],details[1],details[2],details[3]);
         objects.add(p);
       }
     }
     else if(fileName.equals("Admin.txt")){
       for(String s:data){
         String[] details=s.split(",",4);
          Object p=new Admin(details[0],details[1],details[2],details[3]);
          objects.add(p);
       }
     }
     return objects;
   }

   public ArrayList<Product> readProducts(String fileName) throws Exception{
     ArrayList<Object> objects=readObjects(fileName);
     ArrayList<Product> products=new ArrayList<Product>();
     for(Object obj:objects){
       products.add(((Product)obj));
     }
     return products;
   }

   public ArrayList<Transaction> readTransactions(String fileName) throws Exception{
     ArrayList<Object> objects=readObjects(fileName);
     ArrayList<Transaction> transactions=new ArrayList<Transaction>();
     for(Object obj:objects){
       transactions.add(((Transaction)obj));
     }
     return transactions;
   }

   public ArrayList<GiftCard> readGiftCards(String fileName) throws Exception{
     ArrayList<Object> objects=readObjects(fileName);
     ArrayList<GiftCard> giftCards=new ArrayList<GiftCard>();
     for(Object obj:objects){
       giftCards.add(((GiftCard)obj));
     }
     return giftCards;
   }

   public ArrayList<Customer> readCustomers(String fileName) throws Exception{
     ArrayList<Object> objects=readObjects(fileName);
     ArrayList<Customer> customers=new ArrayList<Customer>();
     for(Object obj:objects){
       customers.add(((Customer)obj));
     }
     return customers;
   }

   public ArrayList<Admin> readAdmins(String fileName) throws Exception{
     ArrayList<Object> objects=readObjects(fileName);
     ArrayList<Admin> admins=new ArrayList<Admin>();
     for(Object obj:objects){
       admins.add(((Admin)obj));
     }
     return admins;
   }

   @Override
   public int counter(String fileName) throws Exception{
     int count=0;
     File dir=new File("./../res/");
     File file=new File(dir,fileName);
     boolean fileCheck=false;
     if(file.exists() && !(file).isDirectory()){
         fileCheck=true;
     }
     if(fileCheck){
       if(fileName.equals("Products.txt")){
         ArrayList<Product> prods=(new ConnectTxt()).readProducts("Products.txt");
         count=prods.size();
       }
       else if(fileName.equals("Transactions.txt")){
         ArrayList<Transaction> transax=(new ConnectTxt()).readTransactions("Transactions.txt");
         count=transax.size();
       }
       else if(fileName.equals("Giftcards.txt")){
         ArrayList<GiftCard> cards=(new ConnectTxt()).readGiftCards("Giftcards.txt");
         count=cards.size();
       }
       else if(fileName.equals("Customers.txt")){
         ArrayList<Customer> customers=(new ConnectTxt()).readCustomers("Customers.txt");
         count=customers.size();
       }
       else if(fileName.equals("Admin.txt")){
         ArrayList<Admin> prods=(new ConnectTxt()).readAdmins("Admin.txt");
         count=prods.size();
       }
     }
     return count;
   }

   @Override
   public void remove(String id,String fileName) throws Exception{
     int count=0;
     File dir=new File("./../res/");
     File file=new File(dir,fileName);
     boolean fileCheck=false;
     if(file.exists() && !(file).isDirectory()){
         fileCheck=true;
     }
     if(fileCheck){
       if(fileName.equals("Products.txt")){
         ArrayList<String> productStrings=readStrings("Products.txt");
         ArrayList<String> temp=new ArrayList<String>();
         for(String s:productStrings){
            String[] details=s.split(",",5);
            if(id.equals(details[2])){
              continue;
            }
            s=String.join(",",details);
            temp.add(s);
         }
         try{
           new FileWriter(file).close();
         }
         catch(Exception e){
            e.printStackTrace();
         }
         for(String s:temp){
           try(FileWriter writer=new FileWriter(file,true)){
             writer.write(s);
             writer.write("\n");
             writer.flush();
           }
           catch(Exception e){
              System.out.println("Error while updating product stocks!");
              e.printStackTrace();
           }
         }
       }
       else if(fileName.equals("Transactions.txt")){
         ArrayList<String> stringsOfTransax=readStrings("Transactions.txt");
         ArrayList<String> transaxList=new ArrayList<String>();
         for(String s:stringsOfTransax){
             String[] details=s.split(",");
             if((details[2]).equals(id)){
                continue;
             }
             transaxList.add(s);
         }
         try{
           new FileWriter(file).close();
         }
         catch(Exception e){
           e.printStackTrace();
         }
         for(String s:transaxList){
           try(FileWriter writer=new FileWriter(file,true)){
             writer.write(s);
             writer.write("\n");
             writer.flush();
           }
           catch(Exception e){
             System.out.println("Error while updating Transactions!");
             e.printStackTrace();
           }
         }
       }
       else if(fileName.equals("Giftcards.txt")){
         ArrayList<String> giftStrings=readStrings("Giftcards.txt");
         ArrayList<String> temp=new ArrayList<String>();
         for(String g:giftStrings){
           String[] details=g.split(",",4);
           if(id.equals(details[0])){
             continue;
           }
           temp.add(g);
         }
         try{
           new FileWriter(file).close();
         }
         catch(Exception e){
           e.printStackTrace();
         }
         for(String s:temp){
           try(FileWriter writer=new FileWriter(file,true)){
             writer.write(s);
             writer.write("\n");
             writer.flush();
           }
           catch(Exception e){
             System.out.println("Error while deleting gift card!");
             e.printStackTrace();
           }
         }
       }
     }
   }

   @Override
   public void update(String field,String value,String id,String fileName) throws Exception{
     File dir=new File("./../res/");
     File file=new File(dir,fileName);
     boolean fileCheck=false;
     if(file.exists() && !(file).isDirectory()){
         fileCheck=true;
     }
     if(fileCheck){
       if(fileName.equals("Products.txt")){
         ArrayList<String> productStrings=readStrings("Products.txt");
         ArrayList<String> temp=new ArrayList<String>();
         int check=-1;
         if(field.equals("stock")){
           check=3;
         }
         else if(field.equals("status")){
           check=4;
         }
         else if(field.equals("price")){
           check=1;
         }
         else if(field.equals("addedBy")){
           check=5;
         }
         for(String s:productStrings){
           String[] details=s.split(",",6);
           if(id.equals(details[2])){
             details[check]=value;
           }
           s=String.join(",",details);
           temp.add(s);
         }
         try{
           new FileWriter(file).close();
         }
         catch(Exception e){
           e.printStackTrace();
         }
         for(String s:temp){
           try( FileWriter writer=new FileWriter(file,true)){
             writer.write(s);
             writer.write("\n");
             writer.flush();
           }
           catch(Exception e){
             System.out.println("Error while updating product details!");
             e.printStackTrace();
           }
         }
       }
       else if(fileName.equals("Giftcards.txt")){
         ArrayList<String> giftStrings=readStrings("Giftcards.txt");
         ArrayList<String> temp=new ArrayList<String>();
         int check=-1;
         if(field.equals("balance")){
           check=2;
         }
         else if(field.equals("pin")){
           check=3;
         }
         for(String g:giftStrings){
            String[] details=g.split(",",4);
            if(id.equals(details[0])){
              details[check]=value;
            }
            String ans=String.join(",",details);
            temp.add(ans);
         }
         try{
            new FileWriter(file).close();
         }
         catch(Exception e){
            e.printStackTrace();
         }
         for(String s:temp){
            try(FileWriter writer=new FileWriter(file,true)){
               writer.write(s);
               writer.write("\n");
               writer.flush();
            }
            catch(Exception e){
               System.out.println("Error while updating gift card!");
               e.printStackTrace();
            }
         }
       }
       else if(fileName.equals("Customers.txt")){
          ArrayList<String> customerStrings=readStrings("Customers.txt");
          ArrayList<String> temp=new ArrayList<String>();
          int check=-1;
          if(field.equals("email")){
            check=2;
          }
          else if(field.equals("password")){
            check=3;
          }
          for(String g:customerStrings){
             String[] details=g.split(",",4);
             if(id.equals(details[0])){
                details[check]=value;
             }
             String ans=String.join(",",details);
             temp.add(ans);
          }
          try{
            new FileWriter(file).close();
          }
          catch(Exception e){
            e.printStackTrace();
          }
          for(String s:temp){
            try(FileWriter writer=new FileWriter(file,true)){
              writer.write(s);
              writer.write("\n");
              writer.flush();
            }
            catch(Exception e){
              System.out.println("Error while updating Customers list!");
              e.printStackTrace();
            }
          }
       }
     }
   }

   
}
