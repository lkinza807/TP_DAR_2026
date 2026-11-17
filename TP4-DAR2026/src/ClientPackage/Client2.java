package ClientPackage;

import java.net.*;
import java.util.Scanner;

public class Client2 {
    public static void main(String argv[]) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress adresseServeur = InetAddress.getByName("localhost");
        int portServeur = 1234;

        Thread threadEcoute = new Thread(() -> {
            try {
                byte[] bufferReponse = new byte[1024];
                while (true) {
                    DatagramPacket paquetReponse = new DatagramPacket(bufferReponse, bufferReponse.length);
                    socket.receive(paquetReponse);
                    String msg = new String(paquetReponse.getData(), 0, paquetReponse.getLength());
                    System.out.println("\n" + msg);
                    System.out.print("Moi : ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        threadEcoute.start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Chat UDP démarré ---");

        while (true) {
            System.out.print("Moi : ");
            String message = scanner.nextLine();

            byte[] buffer = message.getBytes();
            DatagramPacket paquetEnvoi = new DatagramPacket(
                    buffer,
                    buffer.length,
                    adresseServeur,
                    portServeur
            );
            socket.send(paquetEnvoi);
        }
    }
}