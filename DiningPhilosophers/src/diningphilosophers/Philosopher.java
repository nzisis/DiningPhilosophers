/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diningphilosophers;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Θανάσης
 */
public class Philosopher extends Thread{

    private Channel leftChannel;
    private Channel rightChannel;
    
    private String name;
    
    private boolean dummy;
    
    public Philosopher(String name,Channel leftChannel,Channel rightChannel){
        this.name = name;
        this.leftChannel = leftChannel;
        this.rightChannel = rightChannel;
    }
    
    @Override
    public void run() {
        int counter = 0;
        while(counter < 2){
            counter++;
            think();
            pickUpLeftChopstick();
            pickUpRightChopstick();
            eat();
            putDownChopsticks();
        }
    }
    
    public void eat(){
        Random rand = new Random();
        int eatTime = rand.nextInt(1000);
         try {
            Thread.sleep(eatTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void think(){
        Random rand = new Random();
        int thinkTime = 1000 + rand.nextInt(1000);
         try {
            Thread.sleep(thinkTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void pickUpLeftChopstick(){
        dummy = leftChannel.receive();
    }
    
    public void pickUpRightChopstick(){
         dummy = rightChannel.receive();
    }
    
    public void putDownChopsticks(){
        leftChannel.send(true);
        rightChannel.send(true);
    }
    
    
}
