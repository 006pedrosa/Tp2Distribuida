

public class Main {
    // variaveis gerais
    private static final int time = 1000;
    private static String ip = "";
    private static final int port = 1024;
    private static final String server = "10.2.7.7";
    public static void main(String[] args) {
        ip = args[0];
        for(int i=0;i<3;i++) {
            Client client = new Client(ip, port);
        }
    }
}