package com.GiftCard.Administration;

import java.util.regex.*;
import java.util.*;
import java.lang.String;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.User.*;
import com.GiftCard.Connectors.*;
import com.GiftCard.Purchase.*;


public class Admin{
  private String aid;
  private String password;
  private String name;
  private String email;

  public void clearConsoleScreen() throws Exception {
       System.out.print("\033[H\033[2J");
       System.out.flush();
  }

  public String getAid(){
    return this.aid;
  }

  public String getPassword(){
    return this.password;
  }

   public String getName(){
    return this.name;
  }

   public String getEmail(){
    return this.email;
  }



  public Admin() throws Exception{
    this.aid=null;
    this.password=null;
    boolean verify=false;
    Scanner sc=new Scanner(System.in);
    System.out.println("");
    System.out.println("------------------------");
    System.out.println("      Admin Login");
    System.out.println("------------------------");
    System.out.print("Enter Admin ID: ");
    String id=sc.nextLine();
    AdminReader ar=new AdminReader();
    if(ar.checkAdminExists(id)){
      boolean flag=true;
      int count=0;
      while(flag){
        System.out.println("");
        System.out.print("Enter password : ");
        String pwd2=sc.nextLine();
        pwd2=pwd2.trim();
        String pwd3=pwd2.toString();
        String pwd=encryptor(pwd3);
        String temp=(((new AdminReader()).getAdmin(id)).getPassword()).toString();
        temp=temp.trim();
        if(pwd.equals(temp)){
          flag=false;
          verify=true;
          System.out.println("Admin login successful");
        }
        else{
          count+=1;
          System.out.println("Invalid password!");
          if(count==3){
            flag=false;
          }
        }
      }
      if(!verify){
        return;
      }
      this.aid=id;
      this.password=((new AdminReader()).getAdmin(id)).getPassword();
    }
    else{
      System.out.println("Security alarm....Invalid admin credentials!");
    }
  }

  public Admin(String id) throws Exception{
    this.aid=null;
    this.password=null;
    Scanner sc=new Scanner(System.in);
     AdminReader ar=new AdminReader();
     if(!(ar.checkAdminExists(id))){
       System.out.println("Enter your name: ");
       this.name=sc.nextLine();
       boolean emailCheck1=true,emailCheck2=true;
       String tempEmail=null;
       while(emailCheck1 || emailCheck2){
        System.out.println("Enter you email id: ");
        tempEmail=sc.nextLine();
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher=pattern.matcher(tempEmail);
        if(matcher.matches()){
          emailCheck1=false;
        }
        else{
          System.out.println("Enter a valid email id!");
        }
        if(!(ar.checkAdminExistsByEmail(tempEmail))){
           emailCheck2=false;
        }
        else{
          System.out.println("Admin already exists!");
        }
       }
       this.email=tempEmail;
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
       boolean flag=true;
       System.out.println("");
       System.out.print("Enter security code to create admin user: ");
       String code=sc.nextLine();
       if(code.equals("Admin123@gift")){
         this.aid=id;
         this.password=pwd1;
         AdminReader saver=new AdminReader();
         Saver data=new ConnectSQL();
         (data).save(this);
       }
       else{
         System.out.println("Invalid security code...Admin creation aborted!");
       }
     }
     else{
       System.out.println("Admin already exists!");
     }
  }

  public  Admin(String id,String pwd,String name,String email){
    this.aid=id;
    this.password=pwd;
    this.email=email;
    this.name=name;
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
  public void addProducts() throws Exception{
    char check='Y';
    while(check=='Y'){
      Scanner sc=new Scanner(System.in);
      System.out.println("Enter product name : ");
      String name=sc.nextLine();
      boolean flagp=true;
      String price=null;
      while(flagp){
        System.out.println("Enter product price: ");
        price=sc.nextLine();
        try{
          float a=Float.parseFloat(price);
          flagp=false;
        }
        catch(NumberFormatException e){
          System.out.println("Please enter a valid price!");
        }
      }
      boolean flag=true;
      String qty=null;
      while(flag){
        System.out.println("Enter product quantity: ");
        qty=sc.nextLine();
        try{
          int a=Integer.parseInt(qty);
          flag=false;
        }
        catch(NumberFormatException e){
          System.out.println("Please enter a valid quantity!");
        }
      }
      Product  p=new Product(name,price,qty,this.getAid());
      if((p.getName())!=null){
        Saver data=new ConnectSQL(); 
        data.save(p);
      }
      else{
        System.out.println("Product already added!");
      }
      System.out.println("Do you want to add more products?(Y/N): ");
      check=sc.next().charAt(0);
      sc.nextLine();
    }
  }

  public void updateStock() throws Exception{
    Scanner sc=new Scanner(System.in);
    System.out.println("<----Product List---->");
    ProductReader pr=new ProductReader();
    pr.printProducts();
    System.out.println("\n");
    char ch='Y';
    while(ch=='Y'){
      System.out.print("Enter product ID: ");
      System.out.println("");
      String prodId=sc.nextLine();
      if(pr.checkProductExists(prodId)){
        boolean flag=true;
        String qty=null;
        while(flag){
          System.out.print("Enter quantity of stock to be added: ");
          qty=sc.nextLine();
          try{
            int temp=Integer.parseInt(qty);
            flag=false;
          }
          catch(NumberFormatException e){
            System.out.println("Enter a valid quantity!");
          }
        }
        (pr.getProduct(prodId)).addStock(qty,this.getAid());
        (pr.getProduct(prodId)).changeAddedBy(this.getAid());
      }
      else{
        System.out.println("Sorry...product does not exist!");
      }
      System.out.println("Do you want to update more products?(Y/N): ");
      ch=sc.next().charAt(0);
      sc.nextLine();
    }
  }

  public void updatePrice() throws Exception{
    Scanner sc=new Scanner(System.in);
    System.out.println("<----Product List---->");
    ProductReader pr=new ProductReader();
    pr.printProducts();
    System.out.println("\n");
    char ch='Y';
    while(ch=='Y'){
      System.out.print("Enter product ID: ");
      System.out.println("");
      String prodId=sc.nextLine();
      if(pr.checkProductExists(prodId)){
        boolean flag=true;
        String qty=null;
        (pr.getProduct(prodId)).changePrice();
      }
      else{
        System.out.println("Sorry...product does not exist!");
      }
      System.out.println("Do you want to update more products?(Y/N): ");
      ch=sc.next().charAt(0);
      sc.nextLine();
    }
  }

  public void deleteProduct() throws Exception{
    Scanner sc=new Scanner(System.in);
    ProductReader pr=new ProductReader();
    Saver data=new ConnectSQL();
    if(((data).counter("Products.txt"))>0){
      System.out.println("<----Product List---->");
    pr.printProducts();
    System.out.println("\n");
    char ch='Y';
    while(ch=='Y'){
      System.out.print("Enter product ID to delete: ");
      System.out.println("");
      String prodId=sc.nextLine();
      if(pr.checkProductExists(prodId)){
        Product p=pr.getProduct(prodId);
        System.out.println("Removing product "+p.getPid()+" will lead to a loss of ???"+((p.getAmount()).multiply(BigDecimal.valueOf(p.getQty())))+" worth "+p.getQty()+" stocks"+"!");
        System.out.println("Are you sure you want to delete this product(Y/N)? : ");
        char check=sc.next().charAt(0);
        if(check=='Y'){
          System.out.println("YES");
           pr.deleteStock(prodId);
        }
      }
      else{
        System.out.println("Sorry...product does not exist!");
      }
      System.out.println("Do you want to delete more products?(Y/N): ");
      ch=sc.next().charAt(0);
      sc.nextLine();
    }
   }
   else{
    System.out.println("No products found in database!");
   }
  }

  public void viewAllProducts() throws Exception{
    ProductReader pr=new ProductReader();
    Saver data=new ConnectSQL();
    if((data).counter("Products.txt")>0){
       System.out.println("");
       System.out.println("========Product List========");
       pr.printProducts();
    }
    else{
      System.out.println("No products added yet!");
    }
  }

  public void viewAllCustomers() throws Exception{
    CustomerReader cr=new CustomerReader();
    if(cr.countCustomers()>0){
       System.out.println("");
       System.out.println("========Customer List========");
       Saver data=new ConnectSQL();
       ArrayList<Customer> customers=(data).readCustomers("Customers.txt");
       for(Customer c:customers){
          c.printCustomerDetails();
       }
    }
    else{
       System.out.println("No customers added yet!");
    }
  }

  public void viewAllGiftCards() throws Exception{
    GiftCardReader cr=new GiftCardReader();
    if(cr.countGiftCards()>0){
      System.out.println("");
      System.out.println("========GiftCard List========");
      Saver data=new ConnectSQL();
      ArrayList<GiftCard> cards=(data).readGiftCards("Giftcards.txt");
      for(GiftCard c:cards){
       c.printGiftCard();
      }
    }
    else{
      System.out.println("No Gift cards issued yet!");
    }
  }

  public void viewAllTransactions() throws Exception{
    TransactionReader cr=new TransactionReader();
    Saver data=new ConnectSQL();
    if((data).counter("Transactions.txt")>0){
      System.out.println("");
      System.out.println("========Transaction List========");
      ArrayList<Transaction> transactions=(data).readTransactions("Transactions.txt");
      for(Transaction c:transactions){
        c.printTransaction();
      }
    }
    else{
      System.out.println("No transactions have been made yet!");
    }
  }



  public static void main(String[] args) throws Exception{
    Admin a=new Admin("00000001");
    Saver s=new ConnectSQL();
    s.save(a);
  }
}


