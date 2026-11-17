package CorbaClient;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;

import org.omg.CORBA.ORB;
import javax.naming.Context;
import javax.naming.InitialContext;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            // a. Initialiser l'ORB (va lire jndi.properties pour trouver l'annuaire)
            ORB orb = ORB.init(args, null);

            // b. Récupérer les propriétés de l'annuaire JNDI et la référence de l'objet distant
            Context ctx = new InitialContext();

            // On cherche l'objet enregistré par le serveur sous le nom "banque"
            org.omg.CORBA.Object objRef = (org.omg.CORBA.Object) ctx.lookup("banque");

            // c. Convertir la référence vers le type IBanqueRemote (Stub)
            IBanqueRemote stub = IBanqueRemoteHelper.narrow(objRef);

            System.out.println("=== Connexion au serveur réussie ===");

            // d. Appeler les méthodes à distance

            // 1. Créer des comptes
            System.out.println("\n--- 1. Création de comptes ---");
            Compte c1 = new Compte(101, 500.0f);
            Compte c2 = new Compte(102, 1200.0f);
            stub.creerCompte(c1);
            stub.creerCompte(c2);
            System.out.println("Comptes 101 et 102 créés.");

            // 2. Verser de l'argent
            System.out.println("\n--- 2. Versement ---");
            stub.verser(250.0f, 101);
            System.out.println("Versement de 250 sur le compte 101 effectué.");

            // 3. Retirer de l'argent
            System.out.println("\n--- 3. Retrait ---");
            stub.retirer(100.0f, 102);
            System.out.println("Retrait de 100 sur le compte 102 effectué.");

            // 4. Consulter un compte spécifique
            System.out.println("\n--- 4. Consultation du compte 101 ---");
            Compte compteConsulte = stub.getCompte(101);
            System.out.println("Code : " + compteConsulte.code);
            System.out.println("Solde : " + compteConsulte.solde);

            // 5. Consulter la liste de tous les comptes
            System.out.println("\n--- 5. Liste de tous les comptes ---");
            Compte[] tousLesComptes = stub.getComptes();
            for (Compte c : tousLesComptes) {
                System.out.println("Compte N°" + c.code + " | Solde : " + c.solde);
            }

            // 6. Conversion Euro vers DT
            System.out.println("\n--- 6. Conversion de devises ---");
            float montantEuro = 100.0f;
            double montantDT = stub.conversion(montantEuro);
            System.out.println(montantEuro + " Euros = " + montantDT + " DT");

            System.out.println("\n=== Fin des opérations ===");

        } catch (Exception e) {
            System.err.println("Erreur Client : " + e.getMessage());
            e.printStackTrace();
        }
    }
}