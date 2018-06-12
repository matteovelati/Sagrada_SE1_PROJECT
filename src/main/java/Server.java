import it.polimi.ingsw.controller.GameController;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        System.setProperty("java.security.policy", "stupid.policy");
        System.setSecurityManager(new SecurityManager());

        GameController network = new GameController();

        Registry registry = LocateRegistry.getRegistry("169.254.148.232");
        registry.bind("network", network);

        System.out.println("Hi! I'm the Sagrada server!\n\nI'm waiting for players...");
    }
}