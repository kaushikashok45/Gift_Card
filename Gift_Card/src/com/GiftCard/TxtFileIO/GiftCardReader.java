package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import com.GiftCard.User.*;
import com.GiftCard.Connectors.*;


public class GiftCardReader extends ConnectTxt{

  
  Saver data=new ConnectTxt();
  
   public boolean checkGiftCardExists(String gid) throws Exception{
    boolean result=false;
     File dir=new File("./../res/");
    File file=new File(dir,"Giftcards.txt");
    if((!(file.exists()))&&((file.isDirectory()))){
      return result;
    }
     ArrayList<GiftCard> giftCards=(data).readGiftCards("Giftcards.txt");


     for(GiftCard g:giftCards){
       if(gid.equals(g.getGiftCardId())){
         result=true;
         break;
       }
     }
     return result;
   }

   public GiftCard getGiftCard(String giftID) throws Exception{

     ArrayList<GiftCard> cards=(data).readGiftCards("Giftcards.txt");
     GiftCard result=null;
     for(GiftCard g:cards){
       if(giftID.equals((g.getGiftCardId()))){
         result=g;
         break;
       }
     }
     return result;
   }

   public ArrayList<GiftCard> getGiftCards(String cid) throws Exception{
     ArrayList<GiftCard> cards=(data).readGiftCards("Giftcards.txt");
     ArrayList<GiftCard> result=new ArrayList<GiftCard>();
     for(GiftCard p:cards){
       if(cid.equals(p.getCustId())){
         result.add(p);
       }
     }
     return result;
   }

   public int countGiftCards() throws Exception{
     int count=data.counter("Giftcards.txt");
     return count;
   }

   public int countGiftCardsOfCust(String cid) throws Exception{
     int count=0;
     ArrayList<GiftCard> cards2=(data).readGiftCards("Giftcards.txt");
     if(cards2!=null){
       ArrayList<GiftCard> cards=new ArrayList<GiftCard>();
       for(GiftCard g:cards2){
         if(cid.equals(g.getCustId())){
           cards.add(g);
         }
       }
       count=cards.size();
     }

     return count;
   }

   public int countCustGiftCards(String cid) throws Exception{
     int count=0;
     ArrayList<GiftCard> tra=this.getGiftCards(cid);
     if(tra!=null){
       count=tra.size();
     }
     return count;
   }

   public void updateBalance(String giftCardID,BigDecimal amount) throws Exception{
      String value=amount.toString();
      data.update("balance",value,giftCardID,"Giftcards.txt");
   }

   public void updatePIN(String giftCardID) throws Exception{
      String pin1="";
      String pin2=null;
      Scanner sc2=new Scanner(System.in);
      do{
        System.out.println("Enter pin for card: ");
        pin1=sc2.nextLine();
        if(!(GiftCard.isNumeric(pin1))){
          System.out.println("Please use only numbers for PIN!");
          continue;
        }
        else if(!pin1.matches("\\S+")){
          continue;
        }
        else if(pin1.length()<4){
          System.out.println("Enter a 4 digit number!");
          continue;
        }
        else if(pin1.length()>4){
          System.out.println("Max 4 digits allowed in pin!");
          continue;
        }
        System.out.println("Re-enter pin for card: ");
        pin2=sc2.nextLine();
        if(!(pin1.equals(pin2))){
          System.out.println("PINs do not match!");
        }
      }while((!(pin1.equals(pin2))));
      data.update("pin",pin1,giftCardID,"Giftcards.txt");
   }

   public boolean blockGiftCard(String gid) throws Exception{
     data.remove(gid,"Giftcards.txt");
     TransactionReader tr=new TransactionReader();
     tr.removeTransactions(gid);
     return true;
   }

 

 }
