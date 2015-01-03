/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diningphilosophers;

/**
 *
 * @author Θανάσης
 */
public class Channel {
    
    boolean message;
    
    Object object;
    
    boolean senderIsReady;
    
    boolean receiverIsReady;
    
    public Channel(){
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
    
    Object object2;
    
    
    public void send(boolean value){
       message = value;
       synchronized(object){
            try{
                senderIsReady = true;
                object.wait();
            }catch(InterruptedException ex){

           }
        }
        senderIsReady = false;
    }
    
    public boolean receive(){
        while(!senderIsReady);
        synchronized(object){
            object.notify();
        }
        return message;
    }
    
    
}
