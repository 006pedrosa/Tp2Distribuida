/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

public class LeaveNetwork implements Runnable {

    Cliente cliente;

    public LeaveNetwork(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        while (true) {
            this.cliente.clientesNaRede.forEach((keyIp, socket) -> {
                if(socket.isConnected()){
                    System.out.println("CLOSED: " + socket.isClosed() + " CONNECTED: " + socket.isConnected() + " IP: " + socket.getInetAddress().getHostAddress());
                }
                if (socket.isClosed()){
                    this.cliente.clientesNaRede.remove(socket.getInetAddress().getHostAddress());
                    System.out.println("No com o ip: " + socket.getInetAddress().getHostAddress() + " desconectou da rede.");
                } 
            });
        }
    }

}
