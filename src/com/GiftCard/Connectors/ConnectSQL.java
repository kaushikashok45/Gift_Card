package com.GiftCard.Connectors;

import com.GiftCard.Administration.*;
import com.GiftCard.Purchase.*;
import com.GiftCard.TxtFileIO.*;
import com.GiftCard.User.*;
import java.io.*;
import java.util.*;
import java.math.BigDecimal;
import java.sql.*;
import java.lang.String;

 public class ConnectSQL implements Saver{

  public Connection getConnection(){
    Connection connection = null;
    try {
        // below two lines are used for connectivity.
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/giftcard",
            "ashok", "Ak2001@rox");

        // mydb is database
        // mydbuser is name of database
        // mydbuser is password of database
    }
    catch (Exception exception) {
        System.out.println(exception);
    }
    return connection;
  }

  public void save(){
    System.out.println("Saved!");
  }

   public void save(Product p) throws Exception{
     /*File dir=new File("./../res/");
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
     }*/
     try{
       Connection c=getConnection();
       String sql = " insert into Products(pid,name,amount,qty,status,addedBy)"+" values (?, ?, ?, ?, ?,?)";
       PreparedStatement preparedStmt = c.prepareStatement(sql);
       preparedStmt.setObject(1,p.getPid(),Types.NUMERIC);
       preparedStmt.setString(2,p.getName());
       preparedStmt.setObject(3,p.getAmount(),Types.REAL);
       preparedStmt.setInt(4,p.getQty());
       preparedStmt.setString(5,p.getStatus());
       preparedStmt.setString(6,p.getAddedBy());
       int i=preparedStmt.executeUpdate();
       System.out.println(i+" records inserted");
       c.close();
     }
     catch(Exception e){ e.printStackTrace();}

   }

   public void save(Transaction t) throws Exception{
     try{
       Connection c=getConnection();
       int len=t.getItems().size();
       for(String s:(t.getItems()).keySet()){
         String sql = " insert into Transactions(tid,giftID,billAmount,timestamp,transaxType,pid,qty)"+" values(?, ?, ?, ?, ?,?,?)";
         PreparedStatement preparedStmt = c.prepareStatement(sql);
         preparedStmt.setString(1,t.getTid());
         preparedStmt.setObject(2,t.getGiftId(),Types.NUMERIC);
         preparedStmt.setObject(3,t.getBillAmount(),Types.REAL);
         preparedStmt.setString(4,t.getTimeStamp());
         preparedStmt.setString(5,(Character.toString(t.getTransaxType())));
         preparedStmt.setObject(6,s,Types.NUMERIC);
         preparedStmt.setInt(7,(t.getItems()).get(s));
         int i=preparedStmt.executeUpdate();
         System.out.println(i+" records inserted");
       }
       c.close();
     }
     catch(Exception e){ System.out.println(e);}
   }

   public void save(GiftCard g) throws Exception{
    try{
     Connection c=getConnection();
     String sql = " insert into GiftCards(gid,cid,balance,pin)"+" values (?, ?, ?, ?)";
     PreparedStatement preparedStmt = c.prepareStatement(sql);
     preparedStmt.setObject(1,g.getGiftCardId(),Types.NUMERIC);
     preparedStmt.setObject(2,g.getCustId(),Types.NUMERIC);
     preparedStmt.setObject(3,g.getBalance(),Types.REAL);
     preparedStmt.setObject(4,g.getPin(),Types.NUMERIC);
     int i=preparedStmt.executeUpdate();
     System.out.println(i+" records inserted");
     c.close();
    }
    catch(Exception e){ e.printStackTrace();}
   }

   public void save(Customer c) throws Exception{
     try{
      Connection cr=getConnection();
      String sql = " insert into Users(cid,name,email,password,type)"+" values (?, ?, ?, ?,?)";
      PreparedStatement preparedStmt = cr.prepareStatement(sql);
      preparedStmt.setString(1,c.getCustId());
      preparedStmt.setString(2,c.getName());
      preparedStmt.setString(3,c.getEmail());
      preparedStmt.setString(4,c.getPassword());
      preparedStmt.setString(5,"Customer");
      int i=preparedStmt.executeUpdate();
      System.out.println(i+" records inserted");
      cr.close();
     }
     catch(Exception e){ e.printStackTrace();}
   }

   public void save(Admin a) throws Exception{
     try{
      Connection c=getConnection();
      String sql = " insert into Users(cid,name,email,password,type)"+" values (?, ?, ?, ?,?)";
      PreparedStatement preparedStmt = c.prepareStatement(sql);
      preparedStmt.setString(1,a.getAid());
      preparedStmt.setString(2,a.getName());
      preparedStmt.setString(3,a.getEmail());
      preparedStmt.setString(4,a.getPassword());
      preparedStmt.setString(5,"Admin");
      int i=preparedStmt.executeUpdate();
      System.out.println(i+" records inserted");
      c.close();
     }
     catch(Exception e){ e.printStackTrace();}
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
     if((readObjects(fileName))!=null){
       if(fileName.equals("Products.txt")){
         ArrayList<Product> prods=(new ConnectSQL()).readProducts("Products.txt");
         count=prods.size();
       }
       else if(fileName.equals("Transactions.txt")){
         ArrayList<Transaction> transax=(new ConnectSQL()).readTransactions("Transactions.txt");
         count=transax.size();
       }
       else if(fileName.equals("Giftcards.txt")){
         ArrayList<GiftCard> cards=(new ConnectSQL()).readGiftCards("Giftcards.txt");
         count=cards.size();
       }
       else if(fileName.equals("Customers.txt")){
         ArrayList<Customer> customers=(new ConnectSQL()).readCustomers("Customers.txt");
         count=customers.size();
       }
       else if(fileName.equals("Admin.txt")){
         ArrayList<Admin> prods=(new ConnectSQL()).readAdmins("Admin.txt");
         count=prods.size();
       }
     }
     return count;
   }

   @Override
   public void remove(String id,String fileName) throws Exception{
     int count=0;
     if((readObjects(fileName))!=null){
       if(fileName.equals("Products.txt")){
         try{
          Connection c=getConnection();
          String sql = "Delete from Products where pid=?";
          PreparedStatement preparedStmt = c.prepareStatement(sql);
          preparedStmt.setObject(1,id,Types.NUMERIC);
          int i=preparedStmt.executeUpdate();
          System.out.println(i+" product deleted");
          c.close();
         }
         catch(Exception e){ System.out.println("Error while deleting product!");}
       }
       else if(fileName.equals("Transactions.txt")){
         try{
          Connection c=getConnection();
          String sql = "Delete from Transactions where giftID=?";
          PreparedStatement preparedStmt = c.prepareStatement(sql);
          preparedStmt.setObject(1,id,Types.NUMERIC);
          int i=preparedStmt.executeUpdate();
          System.out.println(" Transaction deleted");
          c.close();
         }
         catch(Exception e){ e.printStackTrace();System.out.println("Error while deleting Transaction!");}
       }
       else if(fileName.equals("Giftcards.txt")){
         try{
          Connection c=getConnection();
          String sql = "Delete from GiftCards where gid=?";
          PreparedStatement preparedStmt = c.prepareStatement(sql);
          preparedStmt.setObject(1,id,Types.NUMERIC);
          int i=preparedStmt.executeUpdate();
          System.out.println(i+" GiftCard deleted");
          c.close();
         }
         catch(Exception e){ e.printStackTrace();System.out.println("Error while deleting GiftCard!");}
       }
     }
   }

   @Override
   public void update(String field,String value,String id,String fileName) throws Exception{
     if((readObjects(fileName))!=null){
       if(fileName.equals("Products.txt")){
         Connection c=getConnection();
         String check=null;
         String sql = "UPDATE Products set "+field+"=? WHERE pid=?";
         PreparedStatement preparedStmt = c.prepareStatement(sql);
         if(field.equals("qty")){
           preparedStmt.setInt(1,(Integer.parseInt(value)));
           preparedStmt.setObject(2,id,Types.NUMERIC);
         }
         else if(field.equals("status")){
           preparedStmt.setString(1,value);
           preparedStmt.setObject(2,id,Types.NUMERIC);
         }
         else if(field.equals("amount")){
           preparedStmt.setObject(1,value,Types.REAL);
           preparedStmt.setObject(2,id,Types.NUMERIC);
         }
         else if(field.equals("addedBy")){
           preparedStmt.setString(1,value);
           preparedStmt.setObject(2,id,Types.NUMERIC);
         }
         try{
          int i=preparedStmt.executeUpdate();
          System.out.println(i+" "+field+" updated");
          c.close();
         }
         catch(Exception e){ System.out.println("Error while updating products!");}
       }
       else if(fileName.equals("Giftcards.txt")){
         Connection c=getConnection();
         String sql = "UPDATE GiftCards set "+field+"=? WHERE gid=?";
         PreparedStatement preparedStmt = c.prepareStatement(sql);
         String check=null;
         if(field.equals("balance")){
           preparedStmt.setObject(1,value,Types.REAL);
           preparedStmt.setObject(2,id,Types.NUMERIC);
         }
         else if(field.equals("pin")){
           preparedStmt.setObject(1,value,Types.NUMERIC);
           preparedStmt.setObject(2,id,Types.NUMERIC);
         }
         try{
          int i=preparedStmt.executeUpdate();
          System.out.println(i+""+field+" updated");
          c.close();
         }
         catch(Exception e){ System.out.println("Error while updating Gift card!");}
       }
       else if(fileName.equals("Customers.txt")){
          Connection c=getConnection();
          String check=null;
          String sql = "UPDATE Users set "+field+"=? WHERE cid LIKE ?";
          PreparedStatement preparedStmt = c.prepareStatement(sql);
          if(field.equals("email")){
            preparedStmt.setString(1,value);
            preparedStmt.setString(2,id);
          }
          else if(field.equals("password")){
            preparedStmt.setString(1,value);
            preparedStmt.setString(2,id);
          }
          try{
           int i=preparedStmt.executeUpdate();
           System.out.println(i+" "+field+" updated");
           c.close();
          }
          catch(Exception e){ System.out.println("Error while updating Customer details!");}
       }
     }
   }

   public ArrayList<String> readStrings(String fileName) throws Exception{
     Connection c=getConnection();
     ArrayList<String> result=new ArrayList<String>();
     if(fileName.equals("Products.txt")){
       PreparedStatement p = null;
       ResultSet rs = null;
       try{
           String sql = "select * from Products";
           p = c.prepareStatement(sql);
           rs = p.executeQuery();
           while (rs.next()) {

               String pid = String.valueOf(rs.getInt("pid"));
               String name = rs.getString("name");
               String amount = String.valueOf(rs.getBigDecimal("amount"));
               String qty=String.valueOf(rs.getInt("qty"));
               String status=rs.getString("status");
               String addedBy=rs.getString("addedBy");
               String record=name+","+amount+","+pid+","+qty+","+status+","+addedBy;
               result.add(record);
           }
       }
       catch (SQLException e) {
           System.out.println(e);
       }
     }
     else if(fileName.equals("Transactions.txt")){
       PreparedStatement p = null;
       ResultSet rs = null;
       try{
           String sql = "select Distinct tid from Transactions";
           p = c.prepareStatement(sql);
           rs = p.executeQuery();
           while(rs.next()){
             String record=null;
             String tid=rs.getString("tid");
             String gid=null;
             String cid=null;
             String billAmount=null;
             String timestamp=null;
             String transaxType=null;
             String transaxmap=null;
             record=tid+",";
             String sql2 = "select giftID,billAmount,timestamp,transaxType from Transactions where tid LIKE ? ESCAPE '!'";
             PreparedStatement pr2 = c.prepareStatement(sql2);
             pr2.setString(1,tid);
             ResultSet rs2 = pr2.executeQuery();
             while(rs2.next()){
               gid=String.format("%08d",rs2.getInt("giftID"));
               billAmount=String.valueOf(rs2.getBigDecimal("billAmount"));
               timestamp=rs2.getString("timestamp");
               transaxType=rs2.getString("transaxType");
             }
             String sql4 = "select * from GiftCards where gid=?";
             PreparedStatement pr4 = c.prepareStatement(sql4);
             pr4.setObject(1,gid,Types.NUMERIC);
             ResultSet rs4 = pr4.executeQuery();
             while(rs4.next()){
               cid=String.format("%09d",rs4.getInt("cid"));
             }
             record=record+cid+","+gid+","+billAmount+","+timestamp+","+transaxType+",";
             String sql3 = "select pid,qty from Transactions where tid LIKE ? ESCAPE '!'";
             PreparedStatement pr3 = c.prepareStatement(sql3);
             pr3.setString(1,tid);
             ResultSet rs3 = pr3.executeQuery();
             while(rs3.next()){
               String pid=String.valueOf(rs3.getInt("pid"));
               String qty=String.valueOf(rs3.getInt("qty"));
               record=record+pid+":"+qty+"and";
             }
             result.add(record);
           }

       }
       catch (SQLException e) {
           e.printStackTrace();
       }
     }
     else if(fileName.equals("Giftcards.txt")){
       PreparedStatement p = null;
       ResultSet rs = null;
       try{
           String sql = "select * from GiftCards";
           p = c.prepareStatement(sql);
           rs = p.executeQuery();
           while (rs.next()) {
                int temp=rs.getInt("gid");
               String gid = String.format("%08d",temp);
               String cid = String.format("%09d",rs.getInt("cid"));
               String balance = (rs.getBigDecimal("balance")).toString();
               String pin=String.valueOf(rs.getInt("pin"));
               String record=gid+","+cid+","+balance+","+pin;
               result.add(record);
           }
       }
       catch (SQLException e) {
           System.out.println(e);
       }
     }
     else if(fileName.equals("Customers.txt")){
       PreparedStatement p = null;
       ResultSet rs = null;
       try{
           String sql = "select * from Users where type like ? ESCAPE '!'";
           p = c.prepareStatement(sql);
           p.setString(1,"Customer");
           rs = p.executeQuery();
           while (rs.next()){
               String cid = rs.getString("cid");
               String name = rs.getString("name");
               String email=rs.getString("email");
               String password=rs.getString("password");
               String record=cid+","+name+","+email+","+password;
               result.add(record);
           }
       }
       catch (SQLException e){
           System.out.println(e);
       }
     }
     else if(fileName.equals("Admin.txt")){
       PreparedStatement p = null;
       ResultSet rs = null;
       try{
           String sql = "select * from Users where type like ? ESCAPE '!'";
           p = c.prepareStatement(sql);
           p.setString(1,"Admin");
           rs = p.executeQuery();
           while (rs.next()){
               String cid = rs.getString("cid");
               String name = rs.getString("name");
               String email=rs.getString("email");
               String password=rs.getString("password");
               String record=cid+","+password+","+name+","+email;
               result.add(record);
           }
       }
       catch (SQLException e) {
           System.out.println(e);
       }
     }
     return result;
   }

}
