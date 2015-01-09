/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diningphilosophers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Θανάσης
 */
public class DiningPhilosophers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        int numberOfPhilosophers = 5;
        
        final Channel a = new Channel();
        
        Channel b = new Channel();
 
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("First Receiver");
                    a.receive();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t1.start();
        
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    Thread.sleep(3000);
                    System.out.println("Second Receiver");
                    a.receive();
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t2.start();
        
        
        
        //CountDownLatch
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    
                    System.out.println("First Send");
                    a.send(true);
                    Thread.sleep(2000);
                    
                    System.out.println("Second Send");
                    a.send(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DiningPhilosophers.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t3.start();
        
      */
        
        int numberOfPhilosophers = 3;
        
        
        Channel channels[] = new Channel[numberOfPhilosophers];
        for(int i = 0;  i<numberOfPhilosophers; i++){    
           channels[i] = new Channel(""+i);
        }
        
        Fork forks[] = new Fork[numberOfPhilosophers];
        for(int i = 0;i< numberOfPhilosophers;i++){
          //  forks[i] = new Fork(channels[i]);
        }
        
        Philosopher philosophers[] = new Philosopher[numberOfPhilosophers];
        for(int i = 0;i< numberOfPhilosophers;i++){
            boolean turn=true;
            if(i%2==0){
                turn=false;
            }
            philosophers[i] = new Philosopher("" + i,channels[i],channels[(i+1)%numberOfPhilosophers],turn);
        }
        
         
         
        for(int i = 0;i< numberOfPhilosophers;i++){
            forks[i].start();
        }
        
        for(int i = 0;i< numberOfPhilosophers;i++){
          philosophers[i].start();
        }
     
        
        
    }
    
}
