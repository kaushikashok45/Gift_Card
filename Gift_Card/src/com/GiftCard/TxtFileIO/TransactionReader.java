package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import com.GiftCard.User.Transaction;

public class TransactionReader{
 public ArrayList<String> readTransaxStrings(){
   ArrayList<String> stringsOfTransax=new ArrayList<String>();
   File dir=new File("./../res/");
   File file=new File(dir,"Transactions.txt");
   try(FileReader reader=new FileReader(file);BufferedReader buffReader=new BufferedReader(reader)){
     String transax;
     while((transax=(buffReader.readLine()))!=null){
       stringsOfTransax.add(transax);
     }
   }
   catch(IOException e){
     System.out.println("Error retrieving data from file!");
     e.printStackTrace();
   }
   return stringsOfTransax;
 }

  public ArrayList<Transaction> readTransax() throws IOException{
    ArrayList<String> transaxStrings=this.readTransaxStrings();
    ArrayList<Transaction> transaxList=new ArrayList<Transaction>();
    for(String s:transaxStrings){
      String[] details=s.split(",");
      Transaction p=new Transaction(details[0],details[1],details[2],details[3],details[4],details[5],details[6]);
      transaxList.add(p);
    }
    return transaxList;
  }

  public ArrayList<Transaction> getTransax(String cid) throws IOException{
    ArrayList<Transaction> transax=this.readTransax();
    ArrayList<Transaction> result=new ArrayList<Transaction>();
    for(Transaction p:transax){
      if(cid.equals(p.getCustId())){
        result.add(p);
      }
    }
    return result;
  }

  public ArrayList<Transaction> getGiftTransax(String giftID) throws IOException{
    ArrayList<Transaction> transax=this.readTransax();
    ArrayList<Transaction> result=new ArrayList<Transaction>();
    for(Transaction p:transax){
      if(giftID.equals(p.getGiftId())){
        result.add(p);
      }
    }
    return result;
  }


  public void printTransactions() throws IOException{
    ArrayList<Transaction> listTransax=this.readTransax();
    for(Transaction s:listTransax){
      s.printTransaction();
    }
  }

  public int countTransax() throws IOException{
    int count=0;
    boolean fileCheck=false;
     File dir=new File("./../res/");
    File file=new File(dir,"Transactions.txt");
    if(file.exists() && !(file).isDirectory()){
        fileCheck=true;
    }
    if(fileCheck){
      ArrayList<Transaction> transax=this.readTransax();
      count=transax.size();
    }
    return count;
  }

  public int countTransaxByGift(String gid) throws IOException{
    int count=0;
    boolean fileCheck=false;
     File dir=new File("./../res/");
    File file=new File(dir,"Transactions.txt");
    if(file.exists() && !(file).isDirectory()){
        fileCheck=true;
    }
    if(fileCheck){
      ArrayList<Transaction> transax=this.readTransax();
      ArrayList<Transaction> temp=new ArrayList<Transaction>();
      for(Transaction t:transax){
        if(gid.equals(t.getGiftId())){
          temp.add(t);
        }
      }
      count=temp.size();
    }
    return count;
  }

  public int countCustTransax(String cid) throws IOException{
    int count=0;
    boolean fileCheck=false;
     File dir=new File("./../res/");
    File file=new File(dir,"Transactions.txt");
    if(file.exists() && !(file).isDirectory()){
        fileCheck=true;
    }
    if(fileCheck){
      ArrayList<Transaction> transax=this.getTransax(cid);
      count=transax.size();
    }
    return count;
  }

  public void removeTransactions(String gid) throws IOException{
    ArrayList<String> stringsOfTransax=this.readTransaxStrings();
    ArrayList<String> transaxList=new ArrayList<String>();
    for(String s:stringsOfTransax){
      String[] details=s.split(",");
      if((details[2]).equals(gid)){
        continue;
      }
      transaxList.add(s);
    }
    try{
       File dir=new File("./../res/");
       File file=new File(dir,"Transactions.txt");
      new FileWriter(file).close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    for(String s:transaxList){
       File dir=new File("./../res/");
      File file=new File(dir,"Transactions.txt");
      try(FileWriter writer=new FileWriter(file,true)){
        writer.write(s);
        writer.write("\n");
        writer.flush();
      }
      catch(IOException e){
        System.out.println("Error while updating Transactions!");
        e.printStackTrace();
      }
    }
  }
}  