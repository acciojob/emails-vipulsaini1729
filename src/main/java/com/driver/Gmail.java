package com.driver;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;

public class Gmail extends Email {

    public Gmail(String emailId){
        super(emailId);
    }

    class mailInfo{
        Date date;
        String sender;
        String message;

        public mailInfo(Date date, String sender, String message) {
            this.date = date;
            this.sender = sender;
            this.message = message;
        }
    }

    private ArrayList<mailInfo> inboxMessage = new ArrayList<>();
    private ArrayList<mailInfo> trashBin = new ArrayList<>();

    int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)
    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity = inboxCapacity;

    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        mailInfo m = new mailInfo(date, sender, message);
        if(inboxMessage.size() == inboxCapacity){
            trashBin.add(inboxMessage.get(0));
            inboxMessage.remove(0);
        }
        inboxMessage.add(m);
    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        for(int i = 0; i < inboxMessage.size(); i++){
            if(inboxMessage.get(i).message.compareTo(message) == 0){
                trashBin.add(inboxMessage.get(i));
                inboxMessage.remove(i);
            }
        }
    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox
        if(inboxMessage.size() == 0){
            return null;
        }
        int n = inboxMessage.size();
        String latest = inboxMessage.get(n-1).message;
        return latest;
    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        if(inboxMessage.size() == 0) return null;
        String oldestMailMessage = inboxMessage.get(0).message;
        return oldestMailMessage;
    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int count = 0;
        int n = inboxMessage.size();
        for(int i = 0; i < n; i++){
            if(inboxMessage.get(i).date.compareTo(start)==0 || inboxMessage.get(i).date.after(start) && inboxMessage.get(i).date.compareTo(end) == 0 || inboxMessage.get(i).date.before(end)){
                count++;
            }
        }
        return count;

    }

    public int getInboxSize(){
        // Return number of mails in inbox
        return inboxMessage.size();

    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return trashBin.size();
    }

    public void emptyTrash(){
        // clear all mails in the trash
        trashBin.clear();

    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return this.inboxCapacity;
    }
}
