package com.GiftCard.TxtFileIO;

import java.util.*;
import com.GiftCard.Administration.*;
import com.GiftCard.Connectors.*;

public class AdminReader extends ConnectTxt{

   Saver data=new ConnectTxt();
   public boolean checkAdminExists(String aid) throws Exception{
     boolean result=false;
     ArrayList<Admin> listAdmins=(data).readAdmins("Admin.txt");
     if(listAdmins!=null){
       for(Admin p:listAdmins){
         if(aid.equals(p.getAid())){
           result=true;
           break;
         }
       }
     }
     return result;
   }

   public boolean checkAdminExistsByEmail(String email) throws Exception{
     boolean result=false;
     ArrayList<Admin> listAdmins=(data).readAdmins("Admin.txt");
     if(listAdmins!=null){
       for(Admin p:listAdmins){
         if(email.equals(p.getEmail())){
           result=true;
           break;
         }
       }
     }
     return result;
   }

   public Admin getAdmin(String aid) throws Exception{
     ArrayList<Admin> admins=(data).readAdmins("Admin.txt");
     Admin result=null;
     for(Admin p:admins){
       if(aid.equals(p.getAid())){
         result=p;
         break;
       }
     }
     return result;
   }

   public int countAdmins() throws Exception{
     int count=data.counter("Admin.txt");
     return count;
   }

}
