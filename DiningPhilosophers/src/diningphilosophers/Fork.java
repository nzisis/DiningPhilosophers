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

    private Channel leftChannel, rightChannel;

    private boolean dummy1, dummy2;

    public Fork(Channel LeftChannel, Channel RightChannel) {
        this.leftChannel = LeftChannel;
        this.rightChannel = RightChannel;
    }

    @Override
    public void run() {
        while (true) {

            if (leftChannel.send(true,1500)) {
                dummy1 = leftChannel.receive();
            }

            if (rightChannel.send(true,1500)) {
                dummy2 = rightChannel.receive();
            }
        }
    }

}
