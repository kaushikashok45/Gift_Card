package com.GiftCard.main;


import java.util.*;
import java.lang.String;
import java.io.*;
import com.GiftCard.Administration.*;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.Purchase.*;
import com.GiftCard.User.*;
import com.GiftCard.Connectors.*;


public class Application{

  public void clearConsoleScreen() throws Exception {
        System.out.print("\033[H\033[2J");
        System.out.flush();
  }


  public void userRegistration() throws Exception{
    Scanner sc=new Scanner(System.in);
    Customer c=new Customer();
    c.printCustomerDetails();
    System.out.println("Note:Your email ID as will be used as username during customer login!");
    System.out.println("");
  
  }

  public void adminRegistration() throws Exception{
    Scanner sc=new Scanner(System.in);
    System.out.println("");
    System.out.println("------------------------");
    System.out.println("  Admin Registration");
    System.out.println("------------------------");
    System.out.println("");
    System.out.print("Enter admin ID: ");
    String id=sc.nextLine();
    Admin a=new Admin(id);
    System.out.println("");
  
  }


  public void userLogin() throws Exception{
    Scanner sc=new Scanner(System.in);
    boolean fileCheck=false;
    Saver data=new ConnectSQL();
    if((data.counter("Customers.txt"))>0){
        fileCheck=true;
    }

    if(!fileCheck){
      System.out.println("Please try signing up first!");
    
      return;
    }
    System.out.println("------------------------");
    System.out.println("       User Login");
    System.out.println("------------------------");
    Login session=new Login();
    if((session.getCid())!=null){
      Runtime.getRuntime().addShutdownHook(new Thread()
      {
        public void run()
        {
          try{
            if(("login").equals(session.getAction())){
              System.out.println("");
              session.logout();
            }
          }
          catch(Exception e){
            System.out.println("Logout unsuccessful!");
          }
         }
      });
      CustomerReader cr=new CustomerReader();
      Customer c=cr.getCustomer(session.getCid());
      char ch='Y';
      while(ch=='Y'){
        clearConsoleScreen();
        System.out.println("");
        System.out.println("------------------------");
        System.out.println(" Customer Account home");
        System.out.println("------------------------");
        System.out.println("Welcome to your account home, "+(c.getName())+"!");
        System.out.println();
        String temp=null;
        System.out.println("");
        System.out.println("Account actions:");
        System.out.println("a) Enter 1 to create new gift card.");
        System.out.println("b) Enter 2 to recharge existing gift card.");
        System.out.println("c) Enter 3 to view a gift card's transaction history.");
        System.out.println("d) Enter 4 to view all transactions.");
        System.out.println("e) Enter 5 to view a single gift card.");
        System.out.println("e) Enter 6 to view all gift cards.");
        System.out.println("f) Enter 7 to update email.");
        System.out.println("g) Enter 8 to update password.");
        System.out.println("h) Enter 9 to return existing gift card.");
        System.out.println("i) Enter 0 to log out.");
        System.out.println("");
        System.out.print("Enter the number to perform preferred action : ");
        String action=sc.nextLine();
        System.out.println("");
        action=action.trim();
        switch(action){
          case "0":
                  System.out.print("Are you sure you want to log out(Y/N)? : ");
                  String confirm=sc.nextLine();
                  confirm=confirm.trim();
                  if(confirm.equals("Y")){
                    System.out.println("User logout successful!");
                    session.logout();
                    ch='N';
                  }
                  else{
                    System.out.println("Log out cancelled!");
                    System.out.println("Press any key to go back to account home!");
                    temp=sc.nextLine();
                    System.out.println("");
                    continue;
                  }
                  break;

          case "1":
                   GiftCard g=new GiftCard(session.getCid());
                   System.out.println("");
                   System.out.println("Press any key to go back to account home!");
                   temp=sc.nextLine();
                   break;

          case "2":
                  GiftCardReader gr=new GiftCardReader();
                  if((gr.countGiftCardsOfCust(session.getCid()))==0){
                      System.out.println("Please create a gift card first!");
                      System.out.println("");
                      System.out.println("Press any key to go back to account home!");
                      temp=sc.nextLine();
                      break;
                  }
                  session.recharge();
                  System.out.println("");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
                  break;

          case "3":
                  GiftCardReader gr2=new GiftCardReader();
                  if((gr2.countGiftCardsOfCust(session.getCid()))==0){
                        System.out.println("Please create a gift card first!");
                        System.out.println("");
                        System.out.println("Press any key to go back to account home!");
                        temp=sc.nextLine();
                        break;
                  }
                  session.showGiftCardTransax();
                  System.out.println("");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
                  break;

          case "4":
                  CustomerReader cr2=new CustomerReader();
                  TransactionReader tr=new TransactionReader();
                  if((tr.countCustTransax(session.getCid()))==0){
                         System.out.println("Please perform a transaction first!");
                         System.out.println("");
                        System.out.println("Press any key to go back to account home!");
                        temp=sc.nextLine();
                        break;
                  }
                  session.getTransactions();
                  System.out.println("");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
                  break;

          case "5":
                  GiftCardReader gr3=new GiftCardReader();
                  if((gr3.countGiftCardsOfCust(session.getCid()))==0){
                     System.out.println("Please create a gift card first!");
                     System.out.println("");
                     System.out.println("Press any key to go back to account home!");
                     temp=sc.nextLine();
                     break;
                  }
                  session.viewGiftCard();
                  System.out.println("");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
                  break;

          case "6":
                  GiftCardReader gr4=new GiftCardReader();
                  if((gr4.countGiftCardsOfCust(session.getCid()))==0){
                       System.out.println("Please create a gift card first!");
                       System.out.println("");
                       System.out.println("Press any key to go back to account home!");
                       temp=sc.nextLine();
                       break;
                  }
                  session.getGiftCards();
                  System.out.println("");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
                  break;

          case "7":
                  session.emailChange();
                  System.out.println("");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
                  break;

          case "8":
                  session.passwordChange();
                  System.out.println("");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
                  break;

          case "9":
                  GiftCardReader gr5=new GiftCardReader();
                  if((gr5.countGiftCardsOfCust(session.getCid()))==0){
                      System.out.println("Please create a gift card first!");
                      System.out.println("");
                      System.out.println("Press any key to go back to account home!");
                      temp=sc.nextLine();
                      break;
                  }
                  session.returnCard();
                  System.out.println("");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
                  break;

          default:
                  System.out.println("Invalid action selected...please try again!");
                  System.out.println("Press any key to go back to account home!");
                  temp=sc.nextLine();
        }
      }
    }
    else{
     
      return;
    }
 
  }

  public void admin() throws Exception{
    Scanner sc=new Scanner(System.in);
    Saver data=new ConnectSQL(); 
    boolean fileCheck=false;

    if((data.counter("Admin.txt"))>0){
        fileCheck=true;
    }

    if(!fileCheck){
      System.out.println("Please try signing up first!");
    
      return;
    }
    Admin a=new Admin();
    if((a.getAid())!=null){
      char ch='Y';
      while(ch=='Y'){
        clearConsoleScreen();
        System.out.println("");
        System.out.println("------------------------");
        System.out.println(" Administrator Panel");
        System.out.println("------------------------");
        System.out.println("Welcome to your account home, "+(a.getAid())+"!");
        System.out.println();
        String temp=null;
        System.out.println("");
        System.out.println("Account actions:");
        System.out.println("a) Enter 1 to add new Products.");
        System.out.println("b) Enter 2 to update existing stock quantity.");
        System.out.println("c) Enter 3 to update existing stock price.");
        System.out.println("d) Enter 4 to delete Product.");
        System.out.println("e) Enter 5 to view all customers.");
        System.out.println("f) Enter 6 to view all issued gift cards.");
        System.out.println("g) Enter 7 to view all transactions.");
        System.out.println("h) Enter 0 to log out.");
        System.out.println("");
        System.out.print("Enter the number to perform preferred action : ");
        String action=sc.nextLine();
        System.out.println("");
        action=action.trim();
        switch(action){
          case "0":
                  System.out.print("Are you sure you want to log out(Y/N)? : ");
                  String confirm=sc.nextLine();
                  confirm=confirm.trim();
                  if(confirm.equals("Y")){
                    System.out.println("Admin logout successful!");
                    ch='N';
                  }
                  else{
                    System.out.println("Log out cancelled!");
                    System.out.println("Press any key to go back to admin home!");
                    temp=sc.nextLine();
                    System.out.println("");
                    continue;
                  }
                  break;

          case "1":
                   a.addProducts();
                   System.out.println("");
                   System.out.println("Press any key to go back to admin home!");
                   temp=sc.nextLine();
                   break;

          case "2":
                  a.updateStock();
                  System.out.println("");
                  System.out.println("Press any key to go back to admin home!");
                  temp=sc.nextLine();
                  break;

          case "3":
                  a.updatePrice();
                  System.out.println("");
                  System.out.println("Press any key to go back to admin home!");
                  temp=sc.nextLine();
                  break;

          case "4":
                  a.deleteProduct();
                  System.out.println("");
                  System.out.println("Press any key to go back to adminhome!");
                  temp=sc.nextLine();
                  break;

          case "5":
                 a.viewAllCustomers();
                 System.out.println("");
                 System.out.println("Press any key to go back to adminhome!");
                 temp=sc.nextLine();
                 break;

          case "6":
                  a.viewAllGiftCards();
                  System.out.println("");
                  System.out.println("Press any key to go back to adminhome!");
                  temp=sc.nextLine();
                  break;

          case "7":
                   a.viewAllTransactions();
                   System.out.println("");
                   System.out.println("Press any key to go back to adminhome!");
                   temp=sc.nextLine();
                   break;


          default:
                  System.out.println("Invalid action selected...please try again!");
                  System.out.println("Press any key to go back to admin home!");
                  temp=sc.nextLine();
        }
      }
    }
    else{
    
      return;
    }

  }

  public void userPurchase() throws Exception{
    Scanner sc=new Scanner(System.in);
    Saver data=new ConnectSQL(); 
    boolean fileCheck=false;
    if((data.counter("Products.txt"))>0){
        fileCheck=true;
    }

    if(!fileCheck){
      System.out.println("No products found!");
      System.out.println("Press any key to go back to application main menu!");
      String temp=sc.nextLine(); 
      return;
    }
    Purchase p=new Purchase();
    p.listProducts();
    String prods=p.productsChooser();
    String gid=p.getCardDetails();
    if(gid!=null){
      p.makePurchase(prods,gid);
    }
    System.out.println("Press any key to go back to application main menu!");
    String temp=sc.nextLine();
    
  }

  public static void main(String[] args) throws Exception{
    Application app=new Application();
    Scanner sc=new Scanner(System.in);
    char ch='Y';
    String temp=null;
    while(ch=='Y'){
        app.clearConsoleScreen();
        System.out.println("------------------------");
        System.out.println(" Java Gift-Card System");
        System.out.println("------------------------");
        System.out.println("Welcome to java Gift-Card system.");
        System.out.println("");
        System.out.println("App functions:");
        System.out.println("a) Enter 1 to register new user.");
        System.out.println("b) Enter 2 for user login.");
        System.out.println("c) Enter 3 to purchase products.");
        System.out.println("d) Enter 4 to register new admin.");
        System.out.println("e) Enter 5 for administrator login.");
        System.out.println("f) Enter 6 to exit application.");
        System.out.println("");
        System.out.print("Enter preferred function: ");
        String func=sc.nextLine();
        System.out.println("");
        func=func.trim();
        switch(func){
          case "1":
                  app.userRegistration();
                  System.out.println("Press any key to go back to application main menu!");
                  temp=sc.nextLine();
                  break;

          case "2":
                  app.userLogin();
                  System.out.println("Press any key to go back to application main menu!");
                  try{
                     temp=sc.nextLine();
                  }
                  catch(NoSuchElementException e){
                      System.out.println("Goiung back to main menu!");
                  }
                  break;

          case "3":
                  app.userPurchase();
                  break;

          case "4":
                  app.adminRegistration();
                  System.out.println("Press any key to go back to application main menu!");
                  if(sc.hasNextLine()){
                     temp=sc.nextLine();
                  }
                  break;

          case "5":
                  app.admin();
                  System.out.println("Press any key to go back to application main menu!");
                  temp=sc.nextLine();
                  break;

          case "6":
                  System.out.println("Are you sure you want to exit Application(Y/N)? : ");
                  char chr=sc.next().charAt(0);
                  if(chr=='Y'){
                    System.out.println("Exiting application!");
                    System.out.println("Goodbye");
                    ch='N';
                  }
                  else{
                    System.out.println("Press any key to go back to application menu!");
                    temp=sc.nextLine();
                  }
                  break;

          default:
                 System.out.println("Invalid function selected!");
                 System.out.println("Press any key to go back to application menu!");
                 temp=sc.nextLine();
        }
    }
  }
}
