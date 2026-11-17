package ServerPackage;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server2 {
    static byte buffer[] = new byte[1024];
    static List<InetSocketAddress> clientsConnectes = new ArrayList<>();

    public static void main(String argv[]) throws Exception {
        DatagramSocket socket = new DatagramSocket(null);
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(1234));

        System.out.println("Serveur de diffusion démarré...");

        while (true) {
            DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
            socket.receive(paquet);

            InetAddress adresseExpediteur = paquet.getAddress();
            int portExpediteur = paquet.getPort();
            InetSocketAddress adresseCompletExpediteur = new InetSocketAddress(adresseExpediteur, portExpediteur);

            if (!clientsConnectes.contains(adresseCompletExpediteur)) {
                clientsConnectes.add(adresseCompletExpediteur);
                System.out.println("Nouveau client : " + adresseCompletExpediteur);
            }

            int taille = paquet.getLength();
            String messageRecu = new String(paquet.getData(), 0, taille);

            String messageDiffuse = "[" + adresseExpediteur.getHostAddress() + ":" + portExpediteur + "] dit : " + messageRecu;
            byte[] bufferDiffuse = messageDiffuse.getBytes();

            for (InetSocketAddress client : clientsConnectes) {
                if (!client.equals(adresseCompletExpediteur)) {
                    DatagramPacket paquetDiffuse = new DatagramPacket(
                            bufferDiffuse,
                            bufferDiffuse.length,
                            client.getAddress(),
                            client.getPort()
                    );
                    socket.send(paquetDiffuse);
                }
            }
        }
    }
}