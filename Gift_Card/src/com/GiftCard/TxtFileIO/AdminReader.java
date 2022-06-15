package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import com.GiftCard.Administration.*;


public class AdminReader{
  public ArrayList<String> readAdminStrings(){
    ArrayList<String> stringsOfAdmin=new ArrayList<String>();
    File dir=new File("./../res/"); 
    File file=new File(dir,"Admin.txt");
    try(FileReader reader=new FileReader(file);BufferedReader buffReader=new BufferedReader(reader)){
      String admin;
      while((admin=(buffReader.readLine()))!=null){
        stringsOfAdmin.add(admin);
      }
      buffReader.close();
      reader.close();
    }
    catch(IOException e){
      System.out.println("Error retrieving data from file!");
      e.printStackTrace();
    }
    return stringsOfAdmin;
  }

   public ArrayList<Admin> readAdmin() throws IOException{
     ArrayList<String> adminStrings=this.readAdminStrings();
     ArrayList<Admin> admins=new ArrayList<Admin>();
     for(String s:adminStrings){
       String[] details=s.split(",",2);
        Admin p=new Admin(details[0],details[1]);
        admins.add(p);
     }
     return admins;
   }

   public boolean checkAdminExists(String aid) throws IOException{
     boolean result=false;
      File dir=new File("./../res/");
     File file=new File(dir,"Admin.txt");
     if(file.exists() && !(file.isDirectory())){
       ArrayList<Admin> listAdmins=this.readAdmin();
       for(Admin p:listAdmins){
         if(aid.equals(p.getAid())){
           result=true;
           break;
         }
       }
     }
     return result;
   }

   public Admin getAdmin(String aid) throws IOException{
     ArrayList<Admin> admins=this.readAdmin();
     Admin result=null;
     for(Admin p:admins){
       if(aid.equals(p.getAid())){
         result=p;
         break;
       }
     }
     return result;
   }

   public int countAdmins() throws IOException{
     int count=0;
     boolean fileCheck=false;
      File dir=new File("./../res/");
     File file=new File(dir,"Admin.txt");
     if(file.exists() && !(file).isDirectory()){
         fileCheck=true;
     }
     if(fileCheck){
       ArrayList<Admin> prods=this.readAdmin();
       count=prods.size();
     }
     return count;
   }

}