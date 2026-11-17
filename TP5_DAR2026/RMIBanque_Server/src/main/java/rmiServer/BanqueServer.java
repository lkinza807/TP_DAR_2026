package rmiServer;

import rmiService.BanqueImpl;
import rmiService.IBanque;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry; // <-- Ajoutez cet import

public class BanqueServer {
    public static void main(String[] args) {
        try {
            IBanque banque = new BanqueImpl();

            LocateRegistry.createRegistry(1099);

            Context ctx = new InitialContext();
            ctx.rebind("BanqueService", banque);

            System.out.println("Serveur Banque demarre et enregistre dans JNDI.");
            Thread.currentThread().join();

        } catch (Exception e) {
            System.err.println("Erreur Serveur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}