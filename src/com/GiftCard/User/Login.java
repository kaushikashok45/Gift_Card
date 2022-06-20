package com.GiftCard.User;

import java.util.*;
import java.lang.String;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import com.GiftCard.TxtFileIO.*;

public class Login{
  private final String cid;
  private String timestamp;
  private String action;
  private String result;

  public void clearConsoleScreen() throws Exception {
       System.out.print("\033[H\033[2J");
       System.out.flush();
  }

  public Login() throws Exception{
    clearConsoleScreen();
    System.out.println("------------------------");
    System.out.println("       User Login");
    System.out.println("------------------------");
    System.out.println("");
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter registered Email-id: ");
    String cid=sc.nextLine();
    CustomerReader cr=new CustomerReader();
    boolean flag=false;
    if(cr.checkCustomerExistsByEmail(cid)){
      for(int i=0;i<3;i++){
        System.out.println("Enter password: ");
        String pwd=sc.nextLine();
        if(((cr.getCustomerByEmail(cid)).getPassword()).equals((new Customer(pwd)).getPassword())){
          flag=true;
          break;
        }
        else{
          System.out.println("Wrong Password..Try again!");
        }
      }
      if(!flag){
        System.out.println("Wrong password entered 3 times....returning to main menu!");
      }
    }
    else{
      System.out.println("User does not exist!");
    }
    if(!flag){
      Date date=new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      String stamp=formatter.format(date);
      if(cr.checkCustomerExistsByEmail(cid)){
        cid=(cr.getCustomerByEmail(cid)).getCustId();
      }
      this.writeLog("000000000",stamp,"login");
      this.cid=null;
      this.timestamp=null;
      this.action=null;
      this.result=null;
  
      return;
    }
    this.cid=(((new CustomerReader()).getCustomerByEmail(cid)).getCustId());
    this.setTimestamp();
    this.setAction("login");
    this.result="Success";
    System.out.println("User - "+this.getCid()+" login successful!");
    this.writeLog();
  }

  public void logout() throws Exception{
    this.setAction("logout");
    this.setTimestamp();
    System.out.println("Customer logout successful!");
    this.writeLog();
  }

  public void emailChange() throws Exception{
    CustomerReader cr=new CustomerReader();
    Customer c=cr.getCustomer(this.getCid());
    c.changeEmail();
  }

  public void passwordChange() throws Exception{
    CustomerReader cr=new CustomerReader();
    Customer c=cr.getCustomer(this.getCid());
    c.changePassword();
  }

  public boolean createGiftCard() throws Exception{
    GiftCard g=new GiftCard(this.getCid());
    boolean result=false;
    if(g!=null){
        System.out.println("Note:Please top-up your gift card to be able to use it!");
        result=true;
    }
    return result;
  }

  public void recharge() throws Exception{
    clearConsoleScreen();
    System.out.println("------------------------");
    System.out.println("   Gift card top-up");
    System.out.println("------------------------");
    System.out.println("");
    Scanner sc=new Scanner(System.in);
    boolean result=false;
    System.out.println("Enter gift card ID: ");
    String gid=sc.nextLine();
    GiftCardReader gr=new GiftCardReader();
    if(gr.checkGiftCardExists(gid)){
      GiftCard g=gr.getGiftCard(gid);
      String cid=this.getCid();
      if(cid.equals(g.getCustId())){
        System.out.println("Enter card PIN: ");
        String pwd=sc.nextLine();
        if(pwd.equals(g.getPin())){
          System.out.println("Current card balance: "+g.getBalance());
          boolean check=true;
          String amt=null;
          int count=0;
          while(check){
            System.out.println("Enter amount to recharge: ");
            String amt2=sc.nextLine();
            amt2=amt2.trim();
            BigDecimal temp;
            try{
              temp=new BigDecimal(amt2);
            }
            catch(Exception e){
              System.out.println("Enter a valid amount!");
              count+=1;
              if(count<3){
                continue;
              }
              else{
                System.out.println("Recharge failed due to repeated entry of invalid amount!");
           
                return;
              }
            }
            amt=amt2;
            break;
          }
          g.topUp(amt);
          System.out.println("New card balance: "+g.getBalance());
          result=true;
        }
        else{
          System.out.println("Invalid PIN...Recharge failed!");
        }
      }
      else{
        System.out.println("Error..No such card found under your account!");
      }
    }
    else{
      System.out.println("Error....Gift card does not exist!");
    }
  
  }

  public void viewGiftCard() throws Exception{
    Scanner sc=new Scanner(System.in);
    boolean result=false;
    System.out.println("Enter gift card ID: ");
    String gid=sc.nextLine();
    GiftCardReader gr=new GiftCardReader();
    if(gr.checkGiftCardExists(gid)){
      GiftCard g=gr.getGiftCard(gid);
      if((this.getCid()).equals(g.getCustId())){
        System.out.println("Enter card PIN: ");
        String pwd=sc.nextLine();
        if(pwd.equals(g.getPin())){
          g.printGiftCard();
        }
        else{
          System.out.println("Invalid PIN!");
        }
      }
      else{
        System.out.println("Error..No such card found under your account!");
      }
    }
    else{
      System.out.println("Error....Gift card does not exist!");
    }
  
  }

  public void showGiftCardTransax() throws Exception{
    Scanner sc=new Scanner(System.in);
    char result='Y';
    while(result=='Y'){
      System.out.println("Enter gift card ID: ");
      String gid=sc.nextLine();
      GiftCardReader gr=new GiftCardReader();
      if(gr.checkGiftCardExists(gid)){
        GiftCard g=gr.getGiftCard(gid);
        if((this.getCid()).equals(g.getCustId())){
          System.out.println("Enter card PIN: ");
          String pwd=sc.nextLine();
          if(pwd.equals(g.getPin())){
            if(((new TransactionReader()).countTransaxByGift(g.getGiftCardId()))>0){
              g.showTransaction();
            }
            else{
              System.out.println("No transactions have been made with this gift card yet!");
            }
          }
          else{
            System.out.println("Invalid PIN!");
          }
        }
        else{
          System.out.println("Error..No such card found under your account!");
        }
      }
      else{
        System.out.println("Error....Gift card does not exist!");
      }
      System.out.println("Do you want to view the transaction history of other gift card(Y/N)? : ");
      result=sc.next().charAt(0);
      sc.nextLine();
    }
   
  }

  public void returnCard() throws Exception{
    Scanner sc=new Scanner(System.in);
    boolean result=false;
    System.out.println("Enter gift card ID: ");
    String gid=sc.nextLine();
    GiftCardReader gr=new GiftCardReader();
    if(gr.checkGiftCardExists(gid)){
      GiftCard g=gr.getGiftCard(gid);
      if((this.getCid()).equals(g.getCustId())){
        System.out.println("Enter card PIN: ");
        String pwd=sc.nextLine();
        if(pwd.equals(g.getPin())){
          System.out.println("Card has a balance of ₹"+g.getBalance()+" If you return this card,you will lose your balance! "+" Are you sure you want to return this card(Y/N)? :");
          char ch=sc.next().charAt(0);
          if(ch=='Y'){
            gr.blockGiftCard(gid);
            System.out.println("Card returned successfully!");
          }
          else{
            System.out.println("Card return cancelled!");
          }
          
          return;
        }
        else{
          System.out.println("Invalid PIN!");
        }
      }
      else{
        System.out.println("Error..No such card found under your account!");
      }
    }
    else{
      System.out.println("Error....Gift card does not exist!");
    }
    
  }

  public void getTransactions() throws Exception{
    TransactionReader tr=new TransactionReader();
    ArrayList<Transaction> transactions=tr.getTransax(this.getCid());
    for(Transaction t: transactions){
      System.out.println(t.getTid());
      t.printTransaction();
    }
  }

  public void getGiftCards() throws Exception{
    GiftCardReader gr=new GiftCardReader();
    ArrayList<GiftCard> cards=gr.getGiftCards(this.getCid());
    for(GiftCard card:cards){
      card.printGiftCard();
    }
  }

  private void writeLog() throws Exception{
    File dir=new File("./../res/"); 
    File file=new File(dir,"Log.txt");
    try(FileWriter writer=new FileWriter(file,true)){
      String currTransax=this.getCid()+","+this.getTimeStamp()+","+this.getAction()+","+this.getResult();
      writer.write(currTransax);
      writer.write("\n");
      writer.flush();
    }
    catch(Exception e){
      System.out.println("Error while writing log file!");
      e.printStackTrace();
    }
  }



  private void writeLog(String cid,String stamp,String action) throws Exception{
    File file=new File("Log.txt");
    try(FileWriter writer=new FileWriter(file,true)){
      String currTransax=cid+","+stamp+","+action+","+"failed";
      writer.write(currTransax);
      writer.write("\n");
      writer.flush();
    }
    catch(Exception e){
      System.out.println("Error while writing log file!");
      e.printStackTrace();
    }
  }

  public String getCid(){
    return this.cid;
  }

  public String getTimeStamp(){
    return this.timestamp;
  }

  public String getAction(){
    return this.action;
  }

  public String getResult(){
    return this.result;
  }

  private void setAction(String action){
    this.action=action;
  }

  private void setTimestamp(){
    Date date=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    this.timestamp=formatter.format(date);
  }

  public static void main(String[] args) throws Exception{
    Login l=new Login();
    l.createGiftCard();
    l.recharge();
    l.viewGiftCard();
    l.showGiftCardTransax();
    l.getTransactions();
    l.getGiftCards();
    l.logout();
  }
}
