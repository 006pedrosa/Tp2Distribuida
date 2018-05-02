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

/**
 *
 * @author pedro
 */
public class ConnectionRuller implements Runnable {

    Socket socket;
    Cliente cliente;

    ConnectionRuller(Cliente cliente, Socket socket) {
        this.cliente = cliente;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (this.socket.isConnected()) {
            String ipVizinho = this.socket.getInetAddress().getHostAddress();
            System.out.println("Nova conexão com o cliente " + ipVizinho);
            Scanner tipoMensagem;
            try {
                tipoMensagem = new Scanner(this.socket.getInputStream());
                if (tipoMensagem.hasNextLine()) {
                    String tipo = tipoMensagem.nextLine();

                    switch (tipo) {
                        case "NEW":
                            this.cliente.clientesNaRede.put(ipVizinho, this.socket);
                            this.cliente.clientesRequestReply.put(ipVizinho, new RequestReply(false, false));

                            System.out.println("INSERIU VIZINHO " + ipVizinho + " NO MAPA");
                            System.out.println("LISTA DE TODOS OS VIZINHOS: ");
                            cliente.clientesNaRede.forEach((keyIp, socket) -> {
                                System.out.println("IP: " + keyIp + " socket ip: " + socket.getInetAddress().getHostAddress());
                            });
                            break;
                        case "REQUEST":
                            tipo = tipoMensagem.nextLine();
                            String[] mensagem = tipo.split(",");
                            if (Long.parseLong(cliente.hsn) < Long.parseLong(mensagem[0])) {
                                cliente.hsn = mensagem[0];
                            }

                            if (cliente.estado == "LIVRE") {
                                new PrintStream(this.socket.getOutputStream()).println("REPLY");
                            } else if (cliente.estado == "OCUPADO") {
                                cliente.filaEscrita.add(tipo);
                                new PrintStream(this.socket.getOutputStream()).println("RETORNO");
                            } else if (cliente.estado == "AGUARDANDO") {
                                if (Long.parseLong(cliente.filaEscrita.get(0).split(",")[cliente.filaEscrita.size() - 1]) > Long.parseLong(mensagem[0])) {
                                    cliente.filaEscrita.add(tipo);
                                    new PrintStream(this.socket.getOutputStream()).println("RETORNO");
                                } else {
                                    new PrintStream(this.socket.getOutputStream()).println("REPLY");
                                }
                            }
                        default:
                            break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ConnectionRuller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        System.out.println("NO: " + this.cliente.ipRede + this.cliente.digitosFinaisIp + " SE DESCONECTOU DA REDE");
        this.cliente.clientesNaRede.remove(this.socket);
    }

}
