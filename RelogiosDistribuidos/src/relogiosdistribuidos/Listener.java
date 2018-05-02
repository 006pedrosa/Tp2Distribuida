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
                String ipVizinho = conexao.getInetAddress().getHostAddress();
                System.out.println("Nova conexão com o cliente " + ipVizinho);
                Scanner tipoMensagem = new Scanner(conexao.getInputStream());

                if (tipoMensagem.hasNextLine()) {
                    String tipo = tipoMensagem.nextLine();

                    switch (tipo) {
                        case "NEW":
                            this.cliente.clientesNaRede.put(ipVizinho, conexao);
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
                            
                            if(cliente.estado == "LIVRE"){
                                new PrintStream(conexao.getOutputStream()).println("REPLY");
                            }else if(cliente.estado == "OCUPADO"){
                                cliente.filaEscrita.add(tipo);
                                new PrintStream(conexao.getOutputStream()).println("RETORNO");
                            }else if(cliente.estado == "AGUARDANDO"){
                                if (Long.parseLong(cliente.filaEscrita.get(0).split(",")[cliente.filaEscrita.size()-1]) > Long.parseLong(mensagem[0])){
                                    cliente.filaEscrita.add(tipo);
                                    new PrintStream(conexao.getOutputStream()).println("RETORNO");
                                }else{
                                    new PrintStream(conexao.getOutputStream()).println("REPLY");
                                }
                            }
                        default:
                            break;
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
