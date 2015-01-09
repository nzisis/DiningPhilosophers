/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diningphilosophers;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Θανάσης
 */
public class Channel {

    String name;

    BlockingQueue<Boolean> q;

    boolean message;

    Object object;

    volatile boolean senderIsReady;

    Object senderMonitor;

    //boolean receiverIsReady;
    public Channel(String name) {
        this.name = name;
        q = new ArrayBlockingQueue(1);
        senderMonitor = new Object();

        object = new Object();
        message = true;
        senderIsReady = false;
    }

    /*
     public void send(boolean value){
     senderIsReady = true;
     while(!receiverIsReady);
     message = value;
     object.notifyAll();
     }
    
     public boolean receive(){
     receiverIsReady = true;
     while(!senderIsReady);   
     synchronized(object){
     try{
     object.wait();
     }catch(InterruptedException ex){

     }
     }
     return message;
     }*/
    //Object object2;
    public boolean send(boolean value, int timeout) {
        Boolean msg = new Boolean(value);
        try {
            synchronized (senderMonitor) {
                System.out.println(name + " send blocked");
                q.put(msg);
                System.out.println(name + " send unblocked");
                if (timeout == -1) {
                    senderMonitor.wait();
                } else {
                    senderMonitor.wait(timeout);
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                q.remove();
                return false;
            } catch (NoSuchElementException ex) {
                return true;
            }
        }
        /*
         message = value;
         System.out.println("Ready to send...");
         synchronized (object) {
         try {
         System.out.println("Is waiting for receiver");
         senderIsReady = true;
         object.wait();
            
         } catch (InterruptedException ex) {

         }
         }
         senderIsReady = false;
         System.out.println("receiver is ready");
         */
    }

    public boolean receive() {
        try {
            System.out.println(name + " receive blocked");
            Boolean mes = q.take();
            System.out.println(name + " receive unblocked");
            synchronized (senderMonitor) {
                senderMonitor.notify();
            }
            return mes.booleanValue();
        } catch (InterruptedException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return false;
        }

        /*
         synchronized (this) {
         System.out.println("Ready to receive...");
         while (!senderIsReady);

         System.out.println("Sender ready...");
         synchronized (object) {
         object.notify();
         }
         return message;
         }*/
    }

}
