package servidorimpressao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
// classe que vai escutar os outros clientes
public class Listener  implements Runnable{
    int PORTA;
    Relogios relogioAtual;
    ServerSocket server;

    public Listener(Relogios relogio, int PORTA) throws IOException {
        try {
            this.PORTA = PORTA;
            this.relogioAtual = relogio;
            server=new ServerSocket(relogio.id, this.PORTA);
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
