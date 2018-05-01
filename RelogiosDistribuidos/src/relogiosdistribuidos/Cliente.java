/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.io.IOException;
import java.util.HashMap;

public class Cliente implements Runnable {

    public HashMap<String, Cliente> clientesNaRede;
    public String ipRede;
    public String digitosFinaisIp;
    public boolean permissaoParaEscrever;
    public int portaEscuta;
    public Listener listener;
    public Writer writer;
    public JoinNetwork newNo;

    public Cliente(String ip, int porta) throws IOException {
        permissaoParaEscrever = false;

        String[] ipDividido = ip.split("\\.");
        this.ipRede = ipDividido[0] + "." + ipDividido[1] + "." + ipDividido[2] + ".";
        this.digitosFinaisIp = ipDividido[ipDividido.length - 1];

        this.portaEscuta = porta;
        listener = new Listener(this, this.portaEscuta);
        new Thread(listener).start();
        new Thread(this).start();

        
        for(int i=0; i<256; i++){
            newNo = new JoinNetwork(this, i);
            new Thread(newNo).start();
        }
    }

    @Override
    public void run() {
//        while (true) {
//            writer = new Writer(this.ipRede + this.digitosFinaisIp, this.portaEscuta);
//            new Thread(writer).start();
//
//        }
    }

}
