package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import com.GiftCard.User.Customer;
import com.GiftCard.Connectors.*;

public class CustomerReader extends Connect{

 
  Saver data=new ConnectSQL();
  public boolean checkCustomerExists(String cid) throws Exception{
    ArrayList<Customer> customers=(data).readCustomers("Customers.txt");
    boolean result=false;
    for(Customer g:customers){
      if(cid.equals(g.getCustId())){
        result=true;
        break;
      }
    }
    return result;
  }

  public boolean checkCustomerExistsByEmail(String cid) throws Exception{
    ArrayList<Customer> customers=(data).readCustomers("Customers.txt");
    boolean result=false;
    for(Customer g:customers){
      if(cid.equals(g.getEmail())){
        result=true;
        break;
      }
    }
    return result;
  }

  public Customer getCustomer(String cid) throws Exception{
    ArrayList<Customer> customers=(data).readCustomers("Customers.txt");
    Customer result=null;
    for(Customer g:customers){
      if(cid.equals((g.getCustId()))){
        result=g;
        break;
      }
    }
    return result;
  }

  public Customer getCustomerByEmail(String cid) throws Exception{
    ArrayList<Customer> customers=(data).readCustomers("Customers.txt");
    Customer result=null;
    for(Customer g:customers){
      if(cid.equals((g.getEmail()))){
        result=g;
        break;
      }
    }
    return result;
  }



  public int countCustomers() throws Exception{
    int count=data.counter("Customers.txt");
    return count;
  }


  public void updateEmail(String cid,String mailid) throws Exception{
     data.update("email",mailid,cid,"Customers.txt");
  }

  public void updatePassword(String cid,String pwd1) throws Exception{
     data.update("password",pwd1,cid,"Customers.txt");
  }

  public boolean checkNameExists(String name) throws Exception{
    ArrayList<Customer> customers=(data).readCustomers("Customers.txt");;
    boolean result=false;
    if(customers!=null){
      for(Customer g:customers){
        if(name.equals(g.getName())){
          result=true;
          break;
        }
      }
    }
    return result;
  }

  public boolean checkEmailExists(String email) throws Exception{
    ArrayList<Customer> customers=(data).readCustomers("Customers.txt");;
    boolean result=false;
    if(customers!=null){
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
