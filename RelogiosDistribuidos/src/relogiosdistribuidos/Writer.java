/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer implements Runnable{
    private static String ip;
    private static int PORTA;
    Socket socket;

    public Writer(String ip, int porta) {
        this.ip = ip;
        this.PORTA = porta;
    }
    
    
    @Override
    public void run() {
        try {
            socket = new Socket(this.ip, this.PORTA);
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
