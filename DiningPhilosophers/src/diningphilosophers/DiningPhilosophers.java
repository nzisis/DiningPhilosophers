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
public class DiningPhilosophers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int numberOfPhilosophers = 5;
        
           Channel channels[] = new Channel[2*numberOfPhilosophers];
        for(int i = 0;i < numberOfPhilosophers;i++){    
           channels[i] = new Channel();
        }
        
        
        Philosopher philosophers[] = new Philosopher[numberOfPhilosophers];
        for(int i = 0;i< numberOfPhilosophers;i++){
            philosophers[i] = new Philosopher("" + i,channels[i],channels[(i+1)%numberOfPhilosophers]);
        }
        
        Fork forks[] = new Fork[numberOfPhilosophers];
        for(int i = 0;i< numberOfPhilosophers;i++){
            forks[i] = new Fork(channels[i]);
        }
        
        
     
        
        
    }
    
}
