package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import com.GiftCard.User.Customer;

public class CustomerReader{
 public ArrayList<String> readCustomerStrings(){
   ArrayList<String> stringsOfCustomers=new ArrayList<String>();
   File dir=new File("./../res/");
   File file=new File(dir,"Customers.txt");
   try(FileReader reader=new FileReader(file);BufferedReader buffReader=new BufferedReader(reader)){
     String customer;
     while((customer=(buffReader.readLine()))!=null){
       stringsOfCustomers.add(customer);
     }
   }
   catch(IOException e){
     System.out.println("Error retrieving data from file!");
     e.printStackTrace();
   }
   return stringsOfCustomers;
 }

  public ArrayList<Customer> readCustomers() throws IOException{
    ArrayList<String> customerStrings=this.readCustomerStrings();
    ArrayList<Customer> customerList=new ArrayList<Customer>();
    for(String s:customerStrings){
      String[] details=s.split(",",4);
      Customer p=new Customer(details[0],details[1],details[2],details[3]);
      customerList.add(p);
    }
    return customerList;
  }

  public boolean checkCustomerExists(String cid) throws IOException{
    ArrayList<Customer> customers=this.readCustomers();
    boolean result=false;
    for(Customer g:customers){
      if(cid.equals(g.getCustId())){
        result=true;
        break;
      }
    }
    return result;
  }

  public boolean checkCustomerExistsByEmail(String cid) throws IOException{
    ArrayList<Customer> customers=this.readCustomers();
    boolean result=false;
    for(Customer g:customers){
      if(cid.equals(g.getEmail())){
        result=true;
        break;
      }
    }
    return result;
  }

  public Customer getCustomer(String cid) throws IOException{
    ArrayList<Customer> customers=this.readCustomers();
    Customer result=null;
    for(Customer g:customers){
      if(cid.equals((g.getCustId()))){
        result=g;
        break;
      }
    }
    return result;
  }

  public Customer getCustomerByEmail(String cid) throws IOException{
    ArrayList<Customer> customers=this.readCustomers();
    Customer result=null;
    for(Customer g:customers){
      if(cid.equals((g.getEmail()))){
        result=g;
        break;
      }
    }
    return result;
  }



  public int countCustomers() throws IOException{
    int count=0;
    boolean fileCheck=false;
     File dir=new File("./../res/");
    File file=new File(dir,"Customers.txt");
    if(file.exists() && !(file.isDirectory())){
        fileCheck=true;
    }
    if(fileCheck){
      ArrayList<Customer> customers=this.readCustomers();
      count=customers.size();
    }
    return count;
  }


  public void updateEmail(String cid,String mailid) throws IOException{
     ArrayList<String> customerStrings=this.readCustomerStrings();
     ArrayList<String> temp=new ArrayList<String>();
     for(String g:customerStrings){
       String[] details=g.split(",",4);
       if(cid.equals(details[0])){
         details[2]=mailid;
       }
       String ans=String.join(",",details);
       temp.add(ans);
     }
     try{
        File dir=new File("./../res/");
        File file=new File(dir,"Customers.txt");
       new FileWriter(file).close();
     }
     catch(IOException e){
       e.printStackTrace();
     }
     for(String s:temp){
       File dir=new File("./../res/"); 
       File file=new File(dir,"Customers.txt");
       try(FileWriter writer=new FileWriter(file,true)){
         writer.write(s);
         writer.write("\n");
         writer.flush();
       }
       catch(IOException e){
         System.out.println("Error while updating Customers list!");
         e.printStackTrace();
       }
     }
  }

  public void updatePassword(String cid,String pwd1) throws IOException{
     ArrayList<String> custStrings=this.readCustomerStrings();
     ArrayList<String> temp=new ArrayList<String>();
     for(String g:custStrings){
       String[] details=g.split(",",4);
       if(cid.equals(details[0])){
         details[3]=((new Customer(pwd1)).getPassword());
       }
       String ans=String.join(",",details);
       temp.add(ans);
     }
     try{
       File dir=new File("./../res/");
       File file=new File(dir,"Customers.txt");
       new FileWriter(file).close();
     }
     catch(IOException e){
       e.printStackTrace();
     }
     for(String s:temp){
       File dir=new File("./../res/");
       File file=new File(dir,"Customers.txt");
       try(FileWriter writer=new FileWriter(file,true)){
         writer.write(s);
         writer.write("\n");
         writer.flush();
       }
       catch(IOException e){
         System.out.println("Error while updating password!");
         e.printStackTrace();
       }
     }
  }

  public boolean checkNameExists(String name) throws IOException{
    ArrayList<Customer> customers=new ArrayList<Customer>();
    boolean result=false;
    boolean fileCheck=false;
     File dir=new File("./../res/");
    File file=new File(dir,"Customers.txt");
    if(file.exists() && !(file.isDirectory())){
        customers=this.readCustomers();
        fileCheck=true;
    }
    if(fileCheck){
      for(Customer g:customers){
        if(name.equals(g.getName())){
          result=true;
          break;
        }
      }
    }
    return result;
  }

  public boolean checkEmailExists(String email) throws IOException{
    ArrayList<Customer> customers=new ArrayList<Customer>();
    boolean result=false;
    boolean fileCheck=false;
     File dir=new File("./../res/");
    File file=new File(dir,"Customers.txt");
    if(file.exists() && !(file.isDirectory())){
        customers=this.readCustomers();
        fileCheck=true;
    }
    if(fileCheck){
      for(Customer g:customers){
        if(email.equals(g.getEmail())){
          result=true;
          break;
        }
      }
    }
    return result;
  }

}