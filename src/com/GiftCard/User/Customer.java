package com.GiftCard.User;

import java.util.regex.*;
import java.util.*;
import java.lang.String;
import java.io.*;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.TxtFileIO.CustomerReader;
import com.GiftCard.Connectors.*;

public class Customer{
  private final String cid;
  private final String name;
  private String email;
  private String password;

  public void clearConsoleScreen() throws Exception {
       System.out.print("\033[H\033[2J");
       System.out.flush();
  }

   public Customer() throws Exception{
    clearConsoleScreen();
    System.out.println("------------------------");
    System.out.println("       User Signup");
    System.out.println("------------------------");
    System.out.println("");
    this.cid=String.format("%09d",(((new CustomerReader()).countCustomers())+1));
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter your name: ");
    this.name=sc.nextLine();
    boolean check=true;
    String mailid=null;
    while(check){
      System.out.println("Enter your email-ID: ");
      mailid=sc.nextLine();
      String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher=pattern.matcher(mailid);
      if(matcher.matches()){
        break;
      }
      else{
        System.out.println("Enter a valid email ID!");
      }
    }
    CustomerReader cr=new CustomerReader();
    if(cr.checkEmailExists(mailid)){
      System.out.println("User already exists!");
     
      return;
    }
    setEmail(mailid);
    String pwd1="";
    String pwd2=null;
    do{
      System.out.println("Enter password for account: ");
      pwd1=encryptor(sc.nextLine());
      if(pwd1.length()<4){
        System.out.println("Enter a password with more than 4 characters!");
        continue;
      }
      System.out.println("Re-enter password for account: ");
      pwd2=encryptor(sc.nextLine());
      if(!(pwd1.equals(pwd2))){
        System.out.println("Passwords do not match!");
      }
    }while((!(pwd1.equals(pwd2))));
    setPassword(pwd1);
    CustomerReader saver=new CustomerReader();
    Saver data=new ConnectSQL();
    (data).save(this);
 
  }

  public Customer(String pwd){
    this.cid="";
    this.name="";
    this.password=encryptor(pwd);
  }

  public Customer(String cid,String name,String email,String password){
    this.cid=cid;
    this.name=name;
    this.setEmail(email);
    this.setPassword(password);
  }

  public void showGiftCards() throws Exception{
    GiftCardReader gr=new GiftCardReader();
    ArrayList<GiftCard> cards=gr.getGiftCards(this.getCustId());
    for(GiftCard card:cards){
      card.printGiftCard();
    }
  }

  public void showTransactions() throws Exception{
    TransactionReader tr=new TransactionReader();
    ArrayList<Transaction> transactions=tr.getTransax(this.getCustId());
    for(Transaction t:transactions){
      t.printTransaction();
    }
  }

  public void showGiftTransactions() throws Exception{
    Scanner sc=new Scanner(System.in);
    TransactionReader tr=new TransactionReader();
    GiftCardReader gr=new GiftCardReader();
    char ch='Y';
    while(ch=='Y'){
      System.out.println("Enter gift card number: ");
      String id=sc.nextLine();
      if(gr.checkGiftCardExists(id)){
        ArrayList<Transaction> transax=tr.getGiftTransax(id);
        for(Transaction t:transax){
          t.printTransaction();
        }
      }
      else{
        System.out.println("Invalid Gift card id!");
      }
      System.out.println("Do you want to view transactions of another gift card(Y/N)? : ");
      ch=sc.nextLine().charAt(0);
    }
    
  }

  public void removeGiftCard(String gid) throws Exception{
    GiftCardReader gr=new GiftCardReader();
    if(gr.blockGiftCard(gid)){
      System.out.println("Gift card blocked!");
    }
    else{
      System.out.println("Error in blocking card!");
    }
  }

  public void printCustomerDetails(){
    System.out.println("Customer name : "+this.getName());
    System.out.println("Customer ID: "+this.getCustId());
    System.out.println("Customer email id: "+this.getEmail());
  }

  public void changeEmail() throws Exception{
    boolean check=true;
    String mailid=null;
    Scanner sc=new Scanner(System.in);
    while(check){
      System.out.println("Enter new email-ID: ");
      mailid=sc.nextLine();
      String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher=pattern.matcher(mailid);
      if(mailid.equals(((new CustomerReader()).getCustomer(cid)).getEmail())){
        System.out.println("New email should be different from the email currently used!");
      }
      else if(matcher.matches()){
        break;
      }
      else{
        System.out.println("Enter a valid email ID!");
      }
    }
    this.setEmail(mailid);
    CustomerReader cr=new CustomerReader();
    cr.updateEmail(this.getCustId(),mailid);
    System.out.println("E-mail id updated Successfully!");
  
  }

  public void changePassword() throws Exception{
    String pwd1="";
    String pwd2=null;
    Scanner sc2=new Scanner(System.in);
    boolean check=false;
    do{
      System.out.println("Enter new password: ");
      pwd1=sc2.nextLine();
      if(pwd1.length()<4){
        System.out.println("Enter password greater than 4 characters!");
        continue;
      }
      System.out.println("Re-enter new password: ");
      pwd2=sc2.nextLine();
      String enp=(new Customer(pwd1)).getPassword();
      if(!(pwd1.equals(pwd2))){
        System.out.println("Passwords do not match!");
      }
      else if(enp.equals(((new CustomerReader()).getCustomer(cid)).getPassword())){
        System.out.println("New password should be different from the password currently used!");
        check=true;
        continue;
      }
      check=false;
    }while((!(pwd1.equals(pwd2)))||(check));
    this.setPassword(this.encryptor(pwd1));
    CustomerReader cr=new CustomerReader();
    cr.updatePassword(this.getCustId(),this.encryptor(pwd1));
    System.out.println("Password successfully updated!");

  }


 

  public String encryptor(String s){
    StringBuffer result=new StringBuffer("");
    char[] characters=s.toCharArray();
    for(char c:characters){
      if(Character.isAlphabetic(c)){
        char temp=' ';
        if(c=='z'){
          temp='a';
        }
        else if(c=='Z'){
          temp='A';
        }
        else{
          temp=(char)(c+1);
        }
        result.append(temp);
     }
     else{
       result.append(c);
     }

    }
    String resultf=result.toString();
    resultf=resultf.trim();
    return resultf;
  }

  public String getCustId(){
    return this.cid;
  }

  public String getName(){
    return this.name;
  }

  public String getEmail(){
    return this.email;
  }

  public String getPassword(){
    return this.password;
  }

  public void setEmail(String email){
    this.email=email;
  }

  public void setPassword(String pwd){
    this.password=pwd;
  }

  public static void main(String[] args) throws Exception{
    Customer c=new Customer();
    Saver s=new ConnectSQL();
    s.save(c);
  }

}


