import java.io.IOException;

// classe principal do cliente
public class Client implements Runnable {

    public static Client[] otheClients;
    public String id;
    public Listener listener;
    public Client(String id, int port){
        this.id=id;
        try {
            this.listener=new Listener(this, port);
            new Thread(listener).start();
            new Thread(this).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(true){

        }
    }
}
