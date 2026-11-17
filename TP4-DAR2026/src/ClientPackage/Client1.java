package ClientPackage;
import java.net.*;

public class Client1 {

    public static void main(String argv[]) throws Exception {
        DatagramSocket socket = new DatagramSocket();

        Thread threadEcoute = new Thread(new EcouteurUDP(socket));
        threadEcoute.start();

        System.out.println("Client démarré. Prêt à envoyer...");

        for (int i = 0; i < 3; i++) {
            String ch = "Message numéro " + i;
            byte[] buffer = ch.getBytes();

            DatagramPacket donneesEmises = new DatagramPacket(
                    buffer,
                    buffer.length,
                    InetAddress.getByName("localhost"),
                    1234
            );

            socket.send(donneesEmises);
            System.out.println("Envoyé : " + ch);

            Thread.sleep(2000);
        }

        Thread.sleep(2000);
        System.out.println("Fin du programme principal.");
    }
}

class EcouteurUDP implements Runnable {
    private DatagramSocket socket;

    public EcouteurUDP(DatagramSocket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            byte[] bufferReponse = new byte[1024];

            while (true) {
                DatagramPacket paquetReponse = new DatagramPacket(bufferReponse, bufferReponse.length);

                socket.receive(paquetReponse);

                String reponse = new String(paquetReponse.getData(), 0, paquetReponse.getLength());
                System.out.println("[Thread Écoute] Réponse reçue du serveur : " + reponse);
            }
        } catch (Exception e) {
            if (!socket.isClosed()) {
                e.printStackTrace();
            }
        }
    }
}