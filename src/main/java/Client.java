import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.view.cli.ViewCLI;
import it.polimi.ingsw.view.gui.App;
import it.polimi.ingsw.view.gui.ViewGUI;
import javafx.application.Application;

import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws NamingException, RemoteException, NotBoundException {
        Scanner input;
        int val;
        boolean check = false;

        Registry registry = LocateRegistry.getRegistry();

        String remoteObj = "network";

        RemoteGameController centralNetwork = (RemoteGameController) registry.lookup(remoteObj);

        System.out.println("Choose your UI:\n1) CLI\n2) GUI");

        while(!check) {
            input = new Scanner(System.in);

            while (!input.hasNextInt()) {
                System.out.println("Please insert a number.");
                input = new Scanner(System.in);
            }

            val = input.nextInt();

            if (val == 1) {
                new ViewCLI(centralNetwork);
                check = true;
            }
            else if (val == 2) {
                Application.launch(ViewGUI.class);
                check = true;
            }
            else{
                System.out.println("Please select 1 or 2");
                check = false;
            }
        }

    }
}
