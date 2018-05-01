/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Listener implements Runnable{
    private static int PORTA;
    ServerSocket socketEscuta;

    public Listener(int porta) throws IOException {
        this.PORTA = porta;
        try{
            System.out.println("INICIANDO ESCUTA PELA PORTA: " + this.PORTA);
            this.socketEscuta = new ServerSocket(this.PORTA);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Socket conexao = socketEscuta.accept();
                System.out.println("Nova conexão com o cliente " + conexao.getInetAddress().getHostAddress());
                
            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
