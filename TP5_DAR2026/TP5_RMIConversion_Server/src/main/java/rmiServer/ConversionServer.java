package rmiServer;

import rmiService.ConversionImpl;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ConversionServer {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);

            ConversionImpl service = new ConversionImpl();
            System.out.println("Objet créé : " + service.toString());

            Naming.rebind("rmi://localhost:1099/BK", service);
            System.out.println("Serveur RMI de conversion démarré et prêt !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}ml