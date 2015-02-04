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

    private String name;

    private BlockingQueue<Boolean> q;//represents the message that channel transfers
    private Object senderMonitor;//this variable is used for synchronized communication


    public Channel(String name) {
        this.name = name;
        q = new ArrayBlockingQueue(1);
        senderMonitor = new Object();

    }

    /**
     * Inserts the specified element into this queue, waiting up to the
     * specified wait time
     *
     * @param value the value to sent
     * @param timeout if timeout=-1 the channel waits how long to wait before
     * giving up
     * @return
     */
    public boolean send(boolean value, int timeout) {
        Boolean msg = new Boolean(value);

        try {
            synchronized (senderMonitor) {

                q.put(msg);
                if (timeout == -1) {
                    senderMonitor.wait();
                } else {
                    senderMonitor.wait(timeout);
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //prin vgei apo tin sunarthsh paei na afairesei to stoixeio gia na dei an egine h paralavh.
        // An to pire o paraliptis tote h remove() 8a paraksei NoSuchElementException
        finally {
            try {
                q.remove();
                //An den paragei NoSuchElementException shmainei oti den to pire o paraliptis(tautoxrona afairei kai to stoixeio)
                return false;
            } catch (NoSuchElementException ex) {
                //Den exei stoixeio h q, ara to pire o paraliptis
                return true;
            }
        }

    }

    public boolean receive() {
        try {
            
            //Pare to stoixeio 'h perimene mexri na mpei
            Boolean mes = q.take();
            
            //ksipna ton apostolea oti pires to stoixeio
            synchronized (senderMonitor) {
                senderMonitor.notify();
            }
            return mes.booleanValue();
        } catch (InterruptedException ex) {
            Logger.getLogger(Channel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return false;
        }

    }

}
