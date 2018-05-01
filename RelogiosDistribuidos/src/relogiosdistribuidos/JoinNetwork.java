/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JoinNetwork implements Runnable{
    Cliente clienteLocal;
    public int ultimosDigitos;

    public JoinNetwork(Cliente cliente, int ultimosDigitos) {
        this.clienteLocal = cliente;
        this.ultimosDigitos = ultimosDigitos;
    }
    
    @Override
    public void run() {
            if( this.ultimosDigitos != Integer.parseInt(this.clienteLocal.digitosFinaisIp)) {
                try {
                    //System.out.println("ENVIANDO MENSAGEM PARA IP: " + "192.168.0." + Integer.toString(this.ultimosDigitos) );
                    Socket socket = new Socket("192.168.0." + Integer.toString(this.ultimosDigitos), this.clienteLocal.portaEscuta);
                    if (socket.isConnected()){
                        new PrintStream(socket.getOutputStream()).println("NEW");
                        this.clienteLocal.clientesNaRede.put(socket.getInetAddress().getHostAddress(), socket);
                        this.clienteLocal.clientesRequestReply.put(socket.getInetAddress().getHostAddress(), new RequestReply());
                    }
                } catch (IOException ex) {
                    // do nothing
                }
            }
    }
    
}
