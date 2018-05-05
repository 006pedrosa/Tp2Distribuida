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
 *
 * CLASSE RESPONSÁVEL POR GERENCIAR UMA CONEXÃO COM ALGUM NO DA REDE
 *
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
        String ipVizinho = this.socket.getInetAddress().getHostAddress();
        System.out.println("Nova conexão com o cliente " + ipVizinho);

        while (this.socket.isConnected()) {

            Scanner tipoMensagem;
            try {
                tipoMensagem = new Scanner(this.socket.getInputStream());
                if (tipoMensagem.hasNextLine()) {
                    String tipo = tipoMensagem.nextLine();

                    switch (tipo) {
                        case "NEW":

                            // CRIA UMA CONEXAO DE VOLTA
                            try {
                                Socket socketConexaoVolta = new Socket(this.socket.getInetAddress().getHostAddress(), this.cliente.portaEscuta);

                                if (socketConexaoVolta.isConnected()) {
                                    this.cliente.clientesNaRede.put(ipVizinho, socketConexaoVolta);

                                    System.out.println("INSERIU VIZINHO " + ipVizinho + " NO MAPA");
                                    System.out.println("LISTA DE TODOS OS VIZINHOS: ");
                                    this.cliente.clientesNaRede.forEach((keyIp, socket) -> {
                                        System.out.println("NO: " + socket.getInetAddress().getHostAddress());
                                    });
                                    new PrintStream(socketConexaoVolta.getOutputStream()).println("NEW ANSWER");
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(ConnectionRuller.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "REQUEST":
                            tipo = tipoMensagem.nextLine();
                            String[] mensagem = tipo.split(",");
                            System.out.println(mensagem[0]);
//                            if (Long.parseLong(cliente.hsn) < Long.parseLong(mensagem[0])) {
//                                cliente.hsn = mensagem[0];
//                            }

                            if (this.cliente.estado == "LIVRE") {
                                new PrintStream(this.socket.getOutputStream()).println("REPLY");
                            } else if (cliente.estado == "OCUPADO") {
                                //this.cliente.filaEscrita.add(tipo);
                                while (this.cliente.estado != "LIVRE") {
                                    System.out.println("ENTROU COMO: OCUPADO - ESTA COMO: " + this.cliente.estado);
                                    System.out.println("MEU HSN: " + this.cliente.hsn + " HSN DO VIZINHO " + mensagem[1] + " :" + mensagem[0]);
                                    Thread.sleep(5000);
                                }
                                new PrintStream(this.socket.getOutputStream()).println("REPLY");
                            } else if (this.cliente.estado == "AGUARDANDO") {
                                if (Long.parseLong(this.cliente.hsn) < Long.parseLong(mensagem[0])) {
//                                    this.cliente.respostasReply++;
//                                    this.cliente.filaEscrita.add(tipo);
                                    while (this.cliente.estado != "LIVRE") {
                                        System.out.println("ENTROU COMO: AGUARDANDO - ESTA COMO: " + this.cliente.estado);
                                        System.out.println("MEU HSN: " + this.cliente.hsn + " HSN DO VIZINHO " + mensagem[1] + " :" + mensagem[0]);
                                        Thread.sleep(5000);
                                    }
                                    new PrintStream(this.socket.getOutputStream()).println("REPLY");
                                } else {
                                    new PrintStream(this.socket.getOutputStream()).println("REPLY");
                                }
                            }
                            break;
                        case "REPLY":
                            //this.cliente.respostasReply++;
                        default:
                            break;
                    }
                } else {
                    System.out.println("NO: " + socket.getInetAddress().getHostAddress() + " SE DESCONECTOU DA REDE");
                    this.cliente.clientesNaRede.remove(this.socket);
                    //this.cliente.respostasReply++;
                    break;

                }
            } catch (IOException ex) {
                Logger.getLogger(ConnectionRuller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectionRuller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
