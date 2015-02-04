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
        int numberOfPhilosophers = 6;//parameter in main
        int eatingTime=15000;
        int noExecution=10;
        String[] philosophersName={"Aristotle","Plato","Confucius","Socrates","Voltaire","Descartes"};

        Channel channels[] = new Channel[2 * numberOfPhilosophers];
        for (int i = 0; i < 2*numberOfPhilosophers; i++) {
            channels[i] = new Channel("" + i);
        }

        Fork forks[] = new Fork[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new Fork(i,channels[2 * i], channels[2 * i + 1]);
        }

        Philosopher philosophers[] = new Philosopher[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            boolean turn = true;
            if (i % 2 == 0) {
                turn = false;
            }
            int leftIndex = 2 * i - 1;
            if (leftIndex < 0) {
                leftIndex += numberOfPhilosophers * 2;
            }
            int rightIndex = 2 * i;
            philosophers[i] = new Philosopher(philosophersName[i], channels[leftIndex], channels[rightIndex], turn);
            philosophers[i].addConstrain(Philosopher.ExitConstrain.LOOPS, noExecution);
        }

        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i].start();
        }

        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i].start();
        }
        
        
        for (int i = 0; i < numberOfPhilosophers; i++) {
            try {
                philosophers[i].join();
            }catch (InterruptedException ex) {
                
            }
        }
        
     
        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i].stopRunning();
        }
        

    }

}
