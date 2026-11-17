package ServerPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2_1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Serveur en attente d'un client...");

        Socket socket = serverSocket.accept();
        System.out.println("Un client est connecté : "
                + socket.getInetAddress().getHostAddress());

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        int op1;
        do {
            op1 = dis.readInt();
            System.out.println("Opérande 1 : " + op1);

            if (op1 == 0) {
                System.out.println("Le client a demandé la fin.");
                break;
            }

            char operateur = dis.readChar();
            System.out.println("Opérateur : " + operateur);

            int op2 = dis.readInt();
            System.out.println("Opérande 2 : " + op2);

            int resultat = 0;
            boolean erreur = false;

            switch (operateur) {
                case '+': resultat = op1 + op2; break;
                case '-': resultat = op1 - op2; break;
                case '*': resultat = op1 * op2; break;
                case '/':
                    if (op2 == 0) {
                        erreur = true;
                        System.out.println("Division par zéro !");
                    } else {
                        resultat = op1 / op2;
                    }
                    break;
                default:
                    erreur = true;
                    System.out.println("Opérateur inconnu : " + operateur);
            }

            if (erreur) {
                dos.writeInt(Integer.MIN_VALUE);
            } else {
                System.out.println("Calcul : " + op1 + " " + operateur + " " + op2 + " = " + resultat);
                dos.writeInt(resultat);
            }
            dos.flush();

        } while (op1 != 0);

        dis.close();
        dos.close();
        socket.close();
        serverSocket.close();
        System.out.println("Serveur arrêté.");
    }
}