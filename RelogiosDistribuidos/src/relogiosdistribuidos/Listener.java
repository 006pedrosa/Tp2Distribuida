/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
* CLASSE RESPONSÁVEL POR FICAR ESCUTANDO EM UMA DETERMINADA PORTA E ESTABELECER CONEXÕES
*/

public class Listener implements Runnable {

    public int PORTA;
    Cliente cliente;
    ServerSocket socketEscuta;

    public Listener(Cliente cliente, int porta) throws IOException {
        this.PORTA = porta;
        this.cliente = cliente;
        try {
            System.out.println("INICIANDO ESCUTA PELA PORTA: " + this.PORTA);
            this.socketEscuta = new ServerSocket(this.PORTA);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket conexao = socketEscuta.accept();
                if (conexao.isConnected()) {
                    ConnectionRuller conectionRuller = new ConnectionRuller(this.cliente, conexao);
                    new Thread(conectionRuller).start();
                }
            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
