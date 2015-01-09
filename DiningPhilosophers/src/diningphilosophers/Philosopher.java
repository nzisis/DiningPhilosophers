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

    private Channel leftChannel;
    private Channel rightChannel;

    private String name;

    private boolean dummy;
    private boolean turn;

    public Philosopher(String name, Channel leftChannel, Channel rightChannel, boolean turn) {
        this.name = name;
        this.leftChannel = leftChannel;
        this.rightChannel = rightChannel;
        this.turn = turn;
    }

    @Override
    public void run() {
        int counter = 0;
        while (counter < 2) {
            counter++;
            think();
            if (turn) {
                pickUpLeftChopstick();
                //System.out.println(name+" picked the left fork!!!");
                pickUpRightChopstick();
                //System.out.println(name+" picked the right fork!!!");
            } else {
                pickUpRightChopstick();
                pickUpLeftChopstick();
            }
            eat();
            putDownChopsticks();
        }
        System.out.println(name + " IS DONE");
    }

    public void eat() {
        Random rand = new Random();
        System.out.println(name + " is eating!!!");
        int eatTime = rand.nextInt(1000);
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
        System.out.println(name + " picked the left fork!!!");
    }

    public synchronized void pickUpRightChopstick() {
        dummy = rightChannel.receive();
        System.out.println(name + " picked the right fork!!!");
    }

    public synchronized void putDownChopsticks() {
        leftChannel.send(true, -1);
        System.out.println(name + " puts down the left fork");
        rightChannel.send(true, -1);
        System.out.println(name + " puts down the right fork");
    }

}
