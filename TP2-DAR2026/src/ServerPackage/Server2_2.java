package ServerPackage;

import commun.Operation;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class Server2_2 {

        public static void main(String[] args) throws IOException, ClassNotFoundException {

            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Serveur en attente...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connecté");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                Operation op = (Operation) ois.readObject();
                System.out.println("Objet reçu : " + op);

                if (op.getOp1() == 0) {
                    System.out.println("Le client a demandé la fin.");
                    break;
                }

                int op1 = op.getOp1();
                char operateur = op.getOperateur();
                int op2 = op.getOp2();

                int resultat = 0;
                if (operateur == '+') {
                    resultat = op1 + op2;
                } else if (operateur == '-') {
                    resultat = op1 - op2;
                } else if (operateur == '*') {
                    resultat = op1 * op2;
                } else if (operateur == '/') {
                    resultat = op1 / op2;
                }

                System.out.println("Calcul : " + op1 + " " + operateur + " " + op2 + " = " + resultat);

                oos.writeObject(resultat);
                oos.flush();
            }

            ois.close();
            oos.close();
            socket.close();
            serverSocket.close();
            System.out.println("Serveur fermé");
        }
    }

