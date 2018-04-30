package servidorimpressao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class ServidorImpressao {
    public static int PORTA = 2729;
    public static String ip = "127.0.0.1";

    public static void startSender() {
        (new Thread() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket(ip, PORTA);
                    BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(s.getOutputStream()));

                    while (true) {
                        //out.write("Hello World!");
                        out.newLine();
                        out.flush();

                        Thread.sleep(200);
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               
            }
        }).start();
    }

    public static void startServer() {
        (new Thread() {
            @Override
            public void run() {
                ServerSocket ss;
                try {
                    ss = new ServerSocket(PORTA);

                    Socket s = ss.accept();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(s.getInputStream()));
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    PORTA++;
                    startServer();
                }
            }
        }).start();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //startServer();
        //startSender();
        new Thread((Runnable) new Printer(PORTA)).start();
        
            Relogios.relogiosDistribuidos = new Relogios[3];

            for(int i = 0; i < Relogios.relogiosDistribuidos.length; i++){
                Relogios.relogiosDistribuidos[i] = new Relogios(i, PORTA);
            }

            for (Relogios relogiosDistribuido : Relogios.relogiosDistribuidos) {
                try {
                    relogiosDistribuido.Initialize();
                }catch (IOException ex) {
                    Logger.getLogger(ServidorImpressao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }

    
}
