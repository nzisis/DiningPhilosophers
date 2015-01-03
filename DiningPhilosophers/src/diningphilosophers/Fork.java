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
public class Fork extends Thread{
    
    private Channel channel;
    
    
    private boolean dummy;
    
    
    public Fork(Channel channel){
        this.channel = channel;
    }
    
}
