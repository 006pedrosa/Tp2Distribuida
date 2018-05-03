/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class JoinNetwork implements Runnable {

    Cliente clienteLocal;
    public int ultimosDigitos;

    public JoinNetwork(Cliente cliente, int ultimosDigitos) {
        this.clienteLocal = cliente;
        this.ultimosDigitos = ultimosDigitos;
    }

    @Override
    public void run() {
        if (this.ultimosDigitos != Integer.parseInt(this.clienteLocal.digitosFinaisIp)) {
            try {
                Socket socket = new Socket(this.clienteLocal.ipRede + Integer.toString(this.ultimosDigitos), this.clienteLocal.portaEscuta);
                if (socket.isConnected()) {
                    new PrintStream(socket.getOutputStream()).println("NEW");
                    this.clienteLocal.clientesNaRede.put(socket.getInetAddress().getHostAddress(), socket);
                }
            } catch (IOException ex) {
                // do nothing
            }
        }
    }

}
