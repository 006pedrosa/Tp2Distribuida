import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Printer {
    Socket socket;
    PrintWriter out;
    Printer(String server, int port){
        try {
            this.socket = new Socket(server, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void print(int id) throws IOException {
        this.out.println("print," + id);
        this.out.close();
        this.socket.close();
    }
}
