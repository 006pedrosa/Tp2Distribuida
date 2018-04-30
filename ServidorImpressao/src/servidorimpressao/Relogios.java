/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorimpressao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class Relogios implements Runnable{
    public static Relogios[] relogiosDistribuidos;
    public int id;
    public Random registrador;
    public long tempoInicio;
    public Listener listener;
    public int[] permissao;
    boolean permissaoParaImprimir;
    boolean esperandoResposta;
    
    public Relogios(int id){
        this.id=id;
        registrador = new Random();
        tempoInicio = System.currentTimeMillis();
        permissaoParaImprimir = false;
    }
    public void Initialize() throws IOException{
        permissao = new int[Relogios.relogiosDistribuidos.length];

        listener = new Listener(this);
        new Thread(listener).start();
        new Thread(this).start();
    }
    @Override
    public void run() {
        float ordemAleatoria = 5;
        boolean alterar = true;
        esperandoResposta = false;
        while(true){
            if(esperandoResposta == false){
                if((System.currentTimeMillis() - tempoInicio) >= 3000){
                    ordemAleatoria = registrador.nextFloat();
                    tempoInicio = System.currentTimeMillis();
                    alterar = true;
                }
                
                if(ordemAleatoria <= 0.5f) {
                    Arrays.fill(permissao, 0);
                
                    for(int i = 0; i < Relogios.relogiosDistribuidos.length; i++){
                        if(Relogios.relogiosDistribuidos[i] != this){
                            new Thread(new Writer(this, i)).start();
                        }
                    }

                    esperandoResposta = true;
                }
            }
            String texto = "";
            
            texto += "id: "+id+"\nport: " + (2626+id) + "\n"+"Numero aleatorio:"+ordemAleatoria;
            texto += "\n";

            for(int i = 0; i < permissao.length; i++){
                if(Relogios.relogiosDistribuidos[i] == this){
                   texto += "[me] ";
                }
                else{
                    texto += "["+ Integer.toString(permissao[i]) + "] ";
                }
            }
            
            if(permissaoParaImprimir == true){
                texto += "\n[IMPRIMINDO]";
                System.out.println(texto);
                
                ConectarEImprimir();
                
                permissaoParaImprimir = false;
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Relogios.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                continue;
            }
            
            System.out.println(texto);
            
            // se esperando resposta e todo mundo respondeu...
            boolean todosResponderam = true;
            
            for(int i = 0; i < permissao.length; i++){
                if(permissao[i] == 0 && Relogios.relogiosDistribuidos[i] != this){
                    todosResponderam = false;
                    break;
                }
            }
            
            if(esperandoResposta == true && todosResponderam == true){
                boolean liberado = true;
                
                for(int i = 0; i < permissao.length; i++){
                    if(permissao[i] == -1 && Relogios.relogiosDistribuidos[i] != this){
                        liberado = false;
                        break;
                    }
                }
                
                if(liberado == true){
                    permissaoParaImprimir = true;
                }
                
                esperandoResposta = false;
            }
        }
    }
    
        public void ConectarEImprimir(){
        try {
            String pedido;
            String resposta;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            
            Socket clientSocket = new Socket("localhost", 2625);
            
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            pedido = "0123456789";
            outToServer.writeBytes(pedido + "\n");
            
            resposta = inFromServer.readLine();
            System.out.println("FROM PRINTER: " + resposta);
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
    
}
