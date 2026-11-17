package ClientPackage;
import commun.Operation;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class Client2_2 {
        public static void main(String[] args) throws IOException, ClassNotFoundException {

            Socket socket = new Socket("localhost", 1234);
            System.out.println("Client connecté");

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Tapez une opération ou 0 pour quitter.\n");

            while (true) {
                System.out.print("Opération > ");
                String ligne = scanner.nextLine().trim();

                if (ligne.equals("0")) {
                    oos.writeObject(new Operation(0,' ',0));
                    oos.flush();
                    break;
                }

                String[] parties = ligne.split(" ");

                if (parties.length != 3) {
                    System.out.println("Erreur : format = <nombre> <opérateur> <nombre>");
                    continue;
                }

                int op1;
                try {
                    op1 = Integer.parseInt(parties[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : " + parties[0] + " n'est pas un entier");
                    continue;
                }

                if (parties[1].length() != 1 || "+-*/".indexOf(parties[1].charAt(0)) == -1) {
                    System.out.println("Erreur : opérateur invalide (utilisez + - * /)");
                    continue;
                }
                char operateur = parties[1].charAt(0);

                int op2;
                try {
                    op2 = Integer.parseInt(parties[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : " + parties[2] + " n'est pas un entier");
                    continue;
                }

                if (operateur == '/' && op2 == 0) {
                    System.out.println("Erreur : division par zéro impossible");
                    continue;
                }

                Operation op = new Operation(op1, operateur, op2);
                oos.writeObject(op);
                oos.flush();
                System.out.println("Objet envoyé : " + op);

                int resultat = (Integer) ois.readObject();
                System.out.println(">>> Résultat = " + resultat + "\n");
            }

            ois.close();
            oos.close();
            socket.close();
            scanner.close();
            System.out.println("Client fermé");
        }
    }

