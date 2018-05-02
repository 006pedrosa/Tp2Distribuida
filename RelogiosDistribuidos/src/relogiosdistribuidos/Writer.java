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
    Cliente cliente;
    Socket socket;

    public Writer(String ip, int porta, Cliente cliente, String hsn, Socket socket) {
        this.ip = ip;
        this.PORTA = porta;
        this.cliente = cliente;
        this.hsn = hsn;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            new PrintStream(socket.getOutputStream()).println("REQUEST");
            new PrintStream(socket.getOutputStream()).println(hsn + "," + ip);

            Scanner tipoMensagem = new Scanner(socket.getInputStream());

            if (tipoMensagem.hasNextLine()) {
                if (tipoMensagem.nextLine() == "REPLY") {
                    cliente.respostasReply++;
                    cliente.respostas++;
                } else if (tipoMensagem.nextLine() == "RETORNO") {
                    cliente.respostas++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
