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
public class Fork extends Thread {

    private int id;
    private boolean isRunning;
    private boolean dummy1, dummy2;

    private Channel leftChannel, rightChannel;

    public Fork(int id, Channel LeftChannel, Channel RightChannel) {
        this.id = id;
        this.leftChannel = LeftChannel;
        this.rightChannel = RightChannel;
        isRunning = true;
    }

    public void stopRunning() {
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {

            if (leftChannel.isOpen()) {

                if (leftChannel.send(true, 1500)) {
                    dummy1 = leftChannel.receive();
                }
            }
            if (rightChannel.isOpen()) {
                if (rightChannel.send(true, 1500)) {
                    dummy2 = rightChannel.receive();
                }
            }
            
            if(!leftChannel.isOpen() && !rightChannel.isOpen()){
                isRunning=false;
            }
        }
    }

}
