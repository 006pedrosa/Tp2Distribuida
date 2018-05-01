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
import java.util.Random;

public class Writer implements Runnable {

    public String ip;
    public int PORTA;
    Cliente cliente;
    Socket socket;

    public Writer(String ip, int porta, Cliente cliente) {
        this.ip = ip;
        this.PORTA = porta;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        Random gerador = new Random();
        while (true) {
            if (gerador.nextInt(10) >= 5) {
                try {
                    //System.out.println("TENTANDO ACESSAR O IP: " + this.ip + " NA PORTA: " + this.PORTA);
                    socket = new Socket(this.ip, this.PORTA);
                } catch (IOException ex) {
                    Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void conexao(int ultimosDigitos) throws IOException {
        if (ultimosDigitos > 255) {
            return;
        }
        try {
            String[] teste = ip.split(".");
            if (teste[3] != Integer.toString(ultimosDigitos)) {
                socket = new Socket("192.168.0." + ultimosDigitos, this.PORTA);
            }
        } catch (IOException ex) {
            conexao(ultimosDigitos + 1);
        }
    }

}
