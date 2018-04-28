import java.io.IOException;

// classe principal do cliente
public class Client implements Runnable {

    public static Client[] otheClients;
    public int id;
    public Listener listener;
    public Client(int id){
        this.id=id;
        try {
            this.listener=new Listener(this);
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
