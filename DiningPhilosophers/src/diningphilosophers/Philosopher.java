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
public class Philosopher extends Thread {

    private Channel leftChannel;//represents the left Channel of Philosopher
    private Channel rightChannel;//represents the right Channel of Philosopher
    
    private String name;//the name of Philosopher
    private boolean turn;// represents the turn that a philosopher will pick fork (if true he picks first the left fork otherwise the right) 
    private boolean dummy;
    private int noExecution;
    private int eatingTime;
    private int currentEatingTime;
    private int counter;
    
    public Philosopher(String name, Channel leftChannel, Channel rightChannel, boolean turn,int noExecution,int eatingTime) {
        this.name = name;
        this.leftChannel = leftChannel;
        this.rightChannel = rightChannel;
        this.turn = turn;
        this.noExecution=noExecution;
        this.eatingTime=eatingTime;
        this.currentEatingTime=this.counter=0;
    }

    @Override
    public void run() {
  
        while (!isDone()) {
            counter++;
            think();
            if (turn) {
                pickUpLeftChopstick();
                pickUpRightChopstick();

            } else {
                pickUpRightChopstick();
                pickUpLeftChopstick();
            }
            eat();
            putDownChopsticks();
        }
        System.out.println(name + " IS DONE EATING");
    }

    public void eat() {
        Random rand = new Random();
        System.out.println(name + " is eating!!!");
        int eatTime = rand.nextInt(1000);
        currentEatingTime+=eatTime;
        try {
            Thread.sleep(eatTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void think() {
        Random rand = new Random();
        System.out.println(name + " is thinking!!!");
        int thinkTime = 1000 + rand.nextInt(1000);
        try {
            Thread.sleep(thinkTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void pickUpLeftChopstick() {
        dummy = leftChannel.receive();
    }

    public synchronized void pickUpRightChopstick() {
        dummy = rightChannel.receive();
    }

    public synchronized void putDownChopsticks() {
        leftChannel.send(true, -1);
        rightChannel.send(true, -1);
    }

    public boolean isDone(){ 
        if(currentEatingTime>=eatingTime){
            return true;
        }if(counter>noExecution){
            return true;
        }  
        return false;
        
    }
    
    
}
