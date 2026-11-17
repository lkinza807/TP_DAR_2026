package ClientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Client {
    public static void main(String argv[]) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        String ch = " Mon message ";
        int length = ch.length();
        byte buffer[] = ch.getBytes();
        DatagramPacket donneesEmises = new DatagramPacket(buffer,
                length, InetAddress.getByName("localhost"), 1234);
        socket.send(donneesEmises);
    }
}