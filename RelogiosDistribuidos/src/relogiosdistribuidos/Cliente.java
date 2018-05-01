/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.util.HashMap;

public class Cliente implements Runnable{
    private HashMap<String,Cliente> clientesNaRede;
    private String ip;
    private boolean permissaoParaEscrever;
    private int portaEscuta;
    private Listener listener;
    private Writer writer;
   

    public Cliente(String ip, int porta) {
        permissaoParaEscrever = false;
        this.ip = ip;
        this.portaEscuta = porta;
        listener = new Listener(this.ip, portaEscuta);
        new Thread(listener).start();
        writer = new Writer(this.ip, this.portaEscuta);
        new Thread(writer).start();
    }
    
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
