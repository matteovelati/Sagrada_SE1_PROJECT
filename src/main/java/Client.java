import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.view.cli.ViewCLI;

import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws NamingException, RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry();

        String remoteObj = "network";

        RemoteGameController centralNetwork = (RemoteGameController) registry.lookup(remoteObj);

       new ViewCLI(centralNetwork);
    }
}
