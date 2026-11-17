package rmiClient;

import rmiService.IConversion;
import java.rmi.Naming;

public class ConversionClient {
    public static void main(String[] args) {
        try {
            System.out.println("Connexion au serveur RMI...");

            IConversion stub = (IConversion) Naming.lookup("rmi://localhost:1099/BK");

            double result = stub.convertirMontant(200);
            System.out.println("Résultat de la conversion : " + result);

        } catch (Exception e) {
            System.err.println("Erreur Client : " + e.getMessage());
            e.printStackTrace();
        }
    }
}