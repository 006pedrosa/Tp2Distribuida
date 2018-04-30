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
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(int id) throws IOException {
        this.out.println("print," + id);
        this.out.close();
        this.socket.close();
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                Socket connectionSocket = socket.accept();
                
                BufferedReader mensagemRecebida = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream mensagemParaEnviar = new DataOutputStream(connectionSocket.getOutputStream());
                
                String clientSentence = mensagemRecebida.readLine();

                mensagemParaEnviar.writeBytes("ack" + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
}
