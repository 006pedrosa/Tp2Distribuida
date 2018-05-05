/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer implements Runnable {

    public String ip;
    public int PORTA;
    public String hsn;
    public String tipoMensagem;
    Cliente cliente;
    Socket socket;

    public Writer(String ip, int porta, Cliente cliente, String hsn, Socket socket, String tipoMensagem) {
        this.ip = ip;
        this.PORTA = porta;
        this.cliente = cliente;
        this.hsn = hsn;
        this.socket = socket;
        this.tipoMensagem = tipoMensagem;
    }

    @Override
    public void run() {
        try {
            if(this.tipoMensagem == "REQUEST"){
                new PrintStream(this.socket.getOutputStream()).println("REQUEST");
                new PrintStream(this.socket.getOutputStream()).println(hsn + "," + ip);

                Scanner tipoMensagem = new Scanner(this.socket.getInputStream());

                if (tipoMensagem.hasNextLine()) {
                    if (tipoMensagem.nextLine() == "REPLY") {
                        //cliente.respostasReply++;    
                    }
                }
            }else if(this.tipoMensagem == "REPLY"){
                new PrintStream(this.socket.getOutputStream()).println("REPLY");
            }
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
