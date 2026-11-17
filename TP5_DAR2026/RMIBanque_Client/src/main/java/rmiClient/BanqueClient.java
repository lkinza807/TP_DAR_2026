package rmiClient;

import metier.Compte;
import rmiService.IBanque;
import javax.naming.Context;
import javax.naming.InitialContext;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            Context ctx = new InitialContext();
            IBanque banque = (IBanque) ctx.lookup("BanqueService");

            System.out.println("Connecte au service distant via JNDI.");

            Compte c1 = new Compte(101, 1500.50);
            String resCreation = banque.creerCompte(c1);
            System.out.println(resCreation);

            String info = banque.getInfoCompte(101);
            System.out.println(info);

            String infoErreur = banque.getInfoCompte(999);
            System.out.println(infoErreur);

        } catch (Exception e) {
            System.err.println("Erreur Client : " + e.getMessage());
            e.printStackTrace();
        }
    }
}