/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


public class Cliente implements Runnable {

    public static HashMap<String, Socket> clientesNaRede;
    public static HashMap<String, RequestReply> clientesRequestReply;
    
    public String ipRede;
    public String digitosFinaisIp;
    public String estado;
    Queue<String> filaEscrita;
    public int portaEscuta;
    public Listener listener;
    public Writer writer;
    public JoinNetwork newNo;
    public LeaveNetwork controleSaidaNo;

    public Cliente(String ip, int porta) throws IOException {
        estado = "livre";
        filaEscrita = new LinkedList<String>();
        
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
        
        controleSaidaNo = new LeaveNetwork(this);
        new Thread(controleSaidaNo).start();
        
    }

    @Override
    public void run() {
        //writer = new Writer(this.ipRede + this.digitosFinaisIp, this.portaEscuta, this);
        //new Thread(writer).start();

    }

}
