package servidorimpressao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
// classe que vai escutar os outros clientes que vai
public class Listener  implements Runnable{
    Relogios relogioAtual;
    ServerSocket server;

    public Listener(Relogios other) throws IOException {
        try {
            this.relogioAtual = other;
            server=new ServerSocket(500+other.id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try{
            while(true){
                Socket session=this.server.accept();
                
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(session.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(session.getOutputStream());
                
                String clientSentence = inFromClient.readLine();
                //System.out.println("Received: " + clientSentence);
                
                String resposta;
                
                if(relogioAtual.permissaoParaImprimir == false && relogioAtual.esperandoResposta == false){
                    resposta = "1";
                }
                else{
                    resposta = "-1";
                }
                
                outToClient.writeBytes(resposta + "\n");
            }

        }catch (IOException e){
            System.out.println(e);
        }
    }
}
