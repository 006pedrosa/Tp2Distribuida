/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {

    public HashMap<String, Socket> clientesNaRede;
    public HashMap<String, RequestReply> clientesRequestReply;

    public String ipRede;
    public String digitosFinaisIp;
    public String estado;
    public String hsn;

    public int respostasReply;
    public int respostas;
    ArrayList<String> filaEscrita;
    public int portaEscuta;
    public Listener listener;
    public Writer writer;
    public JoinNetwork newNo;
    public LeaveNetwork controleSaidaNo;

    public Cliente(String ip, int porta) throws IOException {
        estado = "LIVRE";
        filaEscrita = new ArrayList<String>();

        clientesNaRede = new HashMap<String, Socket>();
        clientesRequestReply = new HashMap<String, RequestReply>();

        String[] ipDividido = ip.split("\\.");
        this.ipRede = ipDividido[0] + "." + ipDividido[1] + "." + ipDividido[2] + ".";
        this.digitosFinaisIp = ipDividido[ipDividido.length - 1];

        this.portaEscuta = porta;
        listener = new Listener(this, this.portaEscuta);
        new Thread(listener).start();
        new Thread(this).start();

        for (int i = 0; i < 256; i++) {
            newNo = new JoinNetwork(this, i);
            new Thread(newNo).start();
        }

        clientesNaRede.forEach((keyIp, socket) -> {
            System.out.println("IP: " + keyIp + " socket ip: " + socket.getInetAddress().getHostAddress());
        });

//        controleSaidaNo = new LeaveNetwork(this);
//        new Thread(controleSaidaNo).start();
    }

    @Override
    public void run() {
        boolean permissaoEscrita;
        Random gerador = new Random();
        while (true) {
            if (gerador.nextInt(10) >= 5) {
                this.respostas = 0;
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                this.estado = "AGUARDANDO";
                this.hsn = Long.toString(timestamp.getTime() + 1);
                permissaoEscrita = false;
                this.respostas = 0;
                this.respostasReply = 0;

                while (permissaoEscrita == false) {
                    this.clientesNaRede.forEach((keyIp, socket) -> {
                        writer = new Writer(keyIp, this.portaEscuta, this, this.hsn, socket);
                        new Thread(writer).start();

                    });

                    while (this.respostas != this.clientesNaRede.size()) {

                    }

                    if (this.respostasReply == this.clientesNaRede.size()) {
                        permissaoEscrita = true;
                    }
                }

                this.estado = "OCUPADO";
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println((hsn) + " " + (hsn + 1) + " " + (hsn + 2) + " " + (hsn + 3) + " " + (hsn + 4));
                this.estado = "LIVRE";
                // TODO: TERMINAR CÓDIGO APOS SAIR DA SC 
            }
        }

    }

}
