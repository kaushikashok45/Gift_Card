package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import com.GiftCard.User.Transaction;
import com.GiftCard.Connectors.*;

public class TransactionReader extends ConnectTxt{

  Saver data=new ConnectTxt();

  public ArrayList<Transaction> getTransax(String cid) throws Exception{
    ArrayList<Transaction> transax=(data).readTransactions("Transactions.txt");
    ArrayList<Transaction> result=new ArrayList<Transaction>();
    for(Transaction p:transax){
      if(cid.equals(p.getCustId())){
        result.add(p);
      }
    }
    return result;
  }

  public ArrayList<Transaction> getGiftTransax(String giftID) throws Exception{
    ArrayList<Transaction> transax=(data).readTransactions("Transactions.txt");
    ArrayList<Transaction> result=new ArrayList<Transaction>();
    for(Transaction p:transax){
      if(giftID.equals(p.getGiftId())){
        result.add(p);
      }
    }
    return result;
  }


  public void printTransactions() throws Exception{
    ArrayList<Transaction> listTransax=(data).readTransactions("Transactions.txt");
    for(Transaction s:listTransax){
      s.printTransaction();
    }
  }

  public int countTransax() throws Exception{
    int count=data.counter("Transactions.txt");
    return count;
  }

  public int countTransaxByGift(String gid) throws Exception{
    int count=0;
    ArrayList<Transaction> transax=(data).readTransactions("Transactions.txt");
    ArrayList<Transaction> temp=new ArrayList<Transaction>();
    for(Transaction t:transax){
      if(gid.equals(t.getGiftId())){
        temp.add(t);
      }
    }
    count=temp.size();
    return count;
  }

  public int countCustTransax(String cid) throws Exception{
    int count=0;
    if((getTransax(cid)!=null)){
      ArrayList<Transaction> transax=this.getTransax(cid);
      count=transax.size();
    }
    return count;
  }

  public void removeTransactions(String gid) throws Exception{
    data.remove(gid,"Transactions.txt");
  }

    public static void main(String[] args) throws Exception{
    TransactionReader tr=new TransactionReader();
    System.out.println(tr.countCustTransax("000000005"));
   }
}
