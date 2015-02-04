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

    public enum ExitConstrain {

        EATING_TIME, THINKING_TIME, LOOPS
    }

    //represents the channel throught which the Philosopher communicates with the fork on his left side
    private Channel leftChannel;
    //represents the channel throught which the Philosopher communicates with the fork on his right side
    private Channel rightChannel;

    //The Philosopher's name
    private String name;

    //If turn == true then the philosopher picks the left fork first, otherwise he picks the right fork first
    private boolean turn;

    private boolean dummy;

    //Total times that the main loop in the run function will execute
    private int loops;
    private int currentLoop;

    //The total time that the philosopher has to eat before leaving the table, in millis (negative for Infinite)
    private int eatingTime;
    private int currentEatingTime;

    //The total time that the philosopher has to think before leaving the table, in millis (negative for Infinite)
    private int thinkingTime;
    private int currentThinkingTime;

    public Philosopher(String name, Channel leftChannel, Channel rightChannel, boolean turn) {
        this.name = name;
        this.leftChannel = leftChannel;
        this.rightChannel = rightChannel;
        this.turn = turn;
        this.loops = -1;
        this.eatingTime = -1;
        this.thinkingTime = -1;
        this.currentEatingTime = this.currentLoop = this.currentLoop = 0;
    }

    @Override
    public void run() {
        while (!isDone()) {
            currentLoop++;
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
        System.out.println(name + " is eating!!!");

        //Generate a random time to eat
        Random rand = new Random();
        int randMin = 0;
        int randMax = 1000;
        int randomTime = randMin + rand.nextInt(randMax - randMin);
        //If the random time is larger than the time left to eat, set it to that value
        if (eatingTime > 0) {
            if (randomTime > eatingTime - currentEatingTime) {
                randomTime = eatingTime - currentEatingTime;
            }
        }

        try {
            Thread.sleep(randomTime);
            currentEatingTime += randomTime;
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void think() {
        System.out.println(name + " is thinking!!!");

        //Generate a random time to think
        Random rand = new Random();
        int randMin = 1000;
        int randMax = 2000;
        int randomTime = randMin + rand.nextInt(randMax - randMin);

        //If the random time is larger than the time left to think, set it to that value
        if (thinkingTime > 0) {
            if (randomTime > thinkingTime - currentThinkingTime) {
                randomTime = thinkingTime - currentThinkingTime;
            }
        }

        try {
            Thread.sleep(randomTime);
            currentThinkingTime += randomTime;
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

    public void addConstrain(ExitConstrain con, int value) {
        switch (con) {
            case EATING_TIME:
                eatingTime = value;
                break;
            case THINKING_TIME:
                thinkingTime = value;
                break;
            case LOOPS:
                loops = value;
                break;
            default:
                break;
        }
    }

    public boolean isDone() {
        if (eatingTime > 0) {
            if (currentEatingTime >= eatingTime) {
                return true;
            }
        }

        if (thinkingTime > 0) {
            if (currentThinkingTime >= thinkingTime) {
                return true;
            }
        }

        if (loops > 0) {
            if (currentLoop > loops) {
                return true;
            }
        }
        return false;

    }

}
