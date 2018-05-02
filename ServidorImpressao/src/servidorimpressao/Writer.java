/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimpressao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer implements Runnable{
    int PORTA = 2729;
    Relogios relogio;
    int id;
    
    public Writer(Relogios relogio, int id){
        this.relogio = relogio;
        this.id = id;
    }
    
    @Override
    public void run() {
        try {
            
            String recebido;
            String enviado;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            
            Socket socket = new Socket("localhost", PORTA);
            
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            recebido = Integer.toString(relogio.id);
            outToServer.writeBytes(recebido + "\n");
            
            enviado = inFromServer.readLine();
            socket.close();
            
            if(enviado.equals("1")) {
                relogio.permissao[id] = 1;
            }
            else {
                relogio.permissao[id] = -1;
            }
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
    
}
