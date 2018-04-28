import java.io.IOException;
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
            }

        }catch (IOException e){
            System.out.println(e);
        }
    }
}
