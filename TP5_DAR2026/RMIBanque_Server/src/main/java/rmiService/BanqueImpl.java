package rmiService;

import metier.Compte;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class BanqueImpl extends UnicastRemoteObject implements IBanque {

    private Map<Integer, Compte> comptes;

    public BanqueImpl() throws RemoteException {
        super();
        this.comptes = new HashMap<>();
    }

    @Override
    public String creerCompte(Compte c) throws RemoteException {
        if (comptes.containsKey(c.getCode())) {
            return "Erreur : Le compte avec le code " + c.getCode() + " existe deja.";
        }
        comptes.put(c.getCode(), c);
        return "Succes : Compte " + c.getCode() + " cree avec un solde de " + c.getSolde();
    }

    @Override
    public String getInfoCompte(int code) throws RemoteException {
        Compte c = comptes.get(code);
        if (c != null) {
            return c.toString();
        } else {
            return "Erreur : Aucun compte trouve avec le code " + code;
        }
    }
}