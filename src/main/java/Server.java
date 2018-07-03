import it.polimi.ingsw.controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws IOException, AlreadyBoundException, ClassNotFoundException {

        //System.setProperty("java.security.policy", "stupid.policy");
        //System.setSecurityManager(new SecurityManager());

        ServerSocket serverSocket = new ServerSocket(1337);

        GameController network = new GameController(serverSocket, args[0]);

        LocateRegistry.createRegistry(1099);

        Registry registry = LocateRegistry.getRegistry();
        registry.bind("network", network);

        System.out.println("Hi! I'm the Sagrada server!\n\nI'm waiting for players...");
        network.addSocketConnection();
    }
}