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
            System.out.println("TENTANDO ACESSAR O IP: " + this.ip + " NA PORTA: " + this.PORTA);
            socket = new Socket(this.ip, this.PORTA);
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void conexao(int ultimosDigitos) throws IOException {
        if(ultimosDigitos > 255){
            return;
        }
        try{
            String [] teste = ip.split(".");
            if(teste[3] != Integer.toString(ultimosDigitos)){
                socket = new Socket("192.168.0." + ultimosDigitos, this.PORTA);   
            }
        } catch (IOException ex) {
            conexao(ultimosDigitos+1);
        }
    }
    
    
}
