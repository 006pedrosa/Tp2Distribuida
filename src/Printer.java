/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Printer implements Runnable{
    ServerSocket socket;
    PrintWriter out;
    
    Printer(int port){
        try {
            this.socket = new ServerSocket(port+1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Socket connectionSocket = socket.accept();
                
                BufferedReader mensagemRecebida = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream mensagemParaEnviar = new DataOutputStream(connectionSocket.getOutputStream());
                
                String clientSentence = mensagemRecebida.readLine();
                
                for(int i = 0; i < clientSentence.length(); i++){
                    Thread.sleep(500);
                }

                mensagemParaEnviar.writeBytes("ack" + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
