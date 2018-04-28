import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
// classe que vai escutar os outros clientes que vai
public class Listener  implements Runnable{

    ServerSocket server;

    public Listener(Client other) throws IOException {
        try {
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
                BufferedReader inputClient = new BufferedReader(new InputStreamReader(session.getInputStream()));
                DataOutputStream outputClient= new DataOutputStream(session.getOutputStream());

                String write= inputClient.readLine();


            }

        }catch (IOException e){
            System.out.println(e);
        }
    }
}
