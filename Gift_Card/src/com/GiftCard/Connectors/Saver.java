package com.GiftCard.Connectors;


import java.io.*;
import java.util.*;
import com.GiftCard.Administration.*;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.Purchase.*;
import com.GiftCard.User.*;

public interface Saver {
  public void save() throws Exception;
  public void save(Product p) throws Exception;
  public void save(Transaction t) throws Exception;
  public void save(GiftCard g) throws Exception;
  public void save(Customer c) throws Exception;
  public void save(Admin a) throws Exception;
  public ArrayList<String> readStrings(String fileName) throws Exception;
  public ArrayList<Object> readObjects(String fileName) throws Exception;
  public ArrayList<Product> readProducts(String fileName) throws Exception;
  public ArrayList<Transaction> readTransactions(String fileName) throws Exception;
  public ArrayList<GiftCard> readGiftCards(String fileName) throws Exception;
  public ArrayList<Customer> readCustomers(String fileName) throws Exception;
  public ArrayList<Admin> readAdmins(String fileName) throws Exception;
  public int counter(String fileName) throws Exception;
  public void remove(String id,String fileName) throws Exception;
  public void update(String field,String value,String id,String fileName) throws Exception;
}
