/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogiosdistribuidos;

import java.io.IOException;
import java.util.Scanner;

public class RelogiosDistribuidos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Scanner ler = new Scanner(System.in);
        String ip = ler.nextLine();
        Cliente cliente = new Cliente(ip, 5555);

    }

}
