package com.GiftCard.TxtFileIO;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import com.GiftCard.User.*;



public class GiftCardReader{
  public ArrayList<String> readGiftCardStrings(){
    ArrayList<String> stringsOfGiftCards=new ArrayList<String>();
    File dir=new File("./../res/");  
    File file=new File(dir,"Giftcards.txt");
    if((!(file.exists()))&&((file.isDirectory()))){
      System.out.println("No gift card found!");
      return stringsOfGiftCards;
    }
    try(FileReader reader=new FileReader(file);BufferedReader buffReader=new BufferedReader(reader)){
      String giftCard;
      while((giftCard=(buffReader.readLine()))!=null){
        stringsOfGiftCards.add(giftCard);
      }
    }
    catch(IOException e){
      System.out.println("Error retrieving data from file!");
    }
    return stringsOfGiftCards;
  }

   public ArrayList<GiftCard> readGiftCards() throws IOException{
     ArrayList<String> giftCardStrings=this.readGiftCardStrings();
     ArrayList<GiftCard> giftCardList=new ArrayList<GiftCard>();
     for(String s:giftCardStrings){
       String[] details=s.split(",",4);
       GiftCard p=new GiftCard(details[0],details[1],details[2],details[3]);
       giftCardList.add(p);
     }
     return giftCardList;
   }

   public boolean checkGiftCardExists(String gid) throws IOException{
    boolean result=false; 
     File dir=new File("./../res/");
    File file=new File(dir,"Giftcards.txt");
    if((!(file.exists()))&&((file.isDirectory()))){
      return result;
    }
     ArrayList<GiftCard> giftCards=this.readGiftCards();
     
     
     for(GiftCard g:giftCards){
       if(gid.equals(g.getGiftCardId())){
         result=true;
         break;
       }
     }
     return result;
   }

   public GiftCard getGiftCard(String giftID) throws IOException{
    
     ArrayList<GiftCard> cards=this.readGiftCards();
     GiftCard result=null;
     for(GiftCard g:cards){
       if(giftID.equals((g.getGiftCardId()))){
         result=g;
         break;
       }
     }
     return result;
   }

   public ArrayList<GiftCard> getGiftCards(String cid) throws IOException{
     ArrayList<GiftCard> cards=this.readGiftCards();
     ArrayList<GiftCard> result=new ArrayList<GiftCard>();
     for(GiftCard p:cards){
       if(cid.equals(p.getCustId())){
         result.add(p);
       }
     }
     return result;
   }

   public int countGiftCards() throws IOException{
     int count=0;
     boolean fileCheck=false;
      File dir=new File("./../res/");
     File file=new File(dir,"Giftcards.txt");
     if(file.exists() && !(file).isDirectory()){
         fileCheck=true;
     }
     if(fileCheck){
       ArrayList<GiftCard> cards=this.readGiftCards();
       count=cards.size();
     }
     return count;
   }

   public int countGiftCardsOfCust(String cid) throws IOException{
     int count=0;
     boolean fileCheck=false;
      File dir=new File("./../res/");
     File file=new File(dir,"Giftcards.txt");
     if(file.exists() && !(file).isDirectory()){
         fileCheck=true;
     }
     if(fileCheck){
       ArrayList<GiftCard> cards2=this.readGiftCards();
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

   public int countCustGiftCards(String cid) throws IOException{
     int count=0;
     boolean fileCheck=false;
      File dir=new File("./../res/");
     File file=new File(dir,"Giftcards.txt");
     if(file.exists() && !(file.isDirectory())){
         fileCheck=true;
     }
     if(fileCheck){
       ArrayList<GiftCard> tra=this.getGiftCards(cid);
       count=tra.size();
     }
     return count;
   }

   public void updateBalance(String giftCardID,BigDecimal amount){
      ArrayList<String> giftStrings=this.readGiftCardStrings();
      ArrayList<String> temp=new ArrayList<String>();
      for(String g:giftStrings){
        String[] details=g.split(",",4);
        if(giftCardID.equals(details[0])){
          details[2]=amount.toString();
        }
        String ans=String.join(",",details);
        temp.add(ans);
      }
      try{
         File dir=new File("./../res/");
         File file=new File(dir,"Giftcards.txt");
        new FileWriter(file).close();
      }
      catch(IOException e){
        e.printStackTrace();
      }
      for(String s:temp){
         File dir=new File("./../res/");
        File file=new File(dir,"Giftcards.txt");
        try(FileWriter writer=new FileWriter(file,true)){
          writer.write(s);
          writer.write("\n");
          writer.flush();
        }
        catch(IOException e){
          System.out.println("Error while updating gift card!");
          e.printStackTrace();
        }
      }
   }

   public void updatePIN(String giftCardID){
      ArrayList<String> giftStrings=this.readGiftCardStrings();
      ArrayList<String> temp=new ArrayList<String>();
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
      for(String g:giftStrings){
        String[] details=g.split(",",4);
        if(giftCardID.equals(details[0])){
          details[3]=pin1;
        }
        String ans=String.join(",",details);
        System.out.println(ans);
        temp.add(ans);
      }
      try{
         File dir=new File("./../res/");
         File file=new File(dir,"Giftcards.txt");
        new FileWriter(file).close();
      }
      catch(IOException e){
        e.printStackTrace();
      }
      for(String s:temp){
         File dir=new File("./../res/");
        File file=new File(dir,"Giftcards.txt");
        try(FileWriter writer=new FileWriter(file,true)){
          writer.write(s);
          writer.write("\n");
          writer.flush();
        }
        catch(IOException e){
          System.out.println("Error while updating gift card!");
          e.printStackTrace();
        }
      }
      sc2.close();
   }

   public boolean blockGiftCard(String gid) throws IOException{
     ArrayList<String> giftStrings=this.readGiftCardStrings();
     ArrayList<String> temp=new ArrayList<String>();
     for(String g:giftStrings){
       String[] details=g.split(",",4);
       if(gid.equals(details[0])){
         continue;
       }
       temp.add(g);
     }
     try{
       File dir=new File("./../res/");
       File file=new File(dir,"Giftcards.txt");
       new FileWriter(file).close();
     }
     catch(IOException e){
       e.printStackTrace();
     }
     for(String s:temp){
       File dir=new File("./../res/");
       File file=new File(dir,"Giftcards.txt");
       try(FileWriter writer=new FileWriter(file,true)){
         writer.write(s);
         writer.write("\n");
         writer.flush();
       }
       catch(IOException e){
         System.out.println("Error while deleting gift card!");
         e.printStackTrace();
         return false;
       }
     }
     TransactionReader tr=new TransactionReader();
     tr.removeTransactions(gid);
     return true;
   }

 }
