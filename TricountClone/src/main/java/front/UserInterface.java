package front;

import backend.BackendException;
import backend.BackendSession;

public class UserInterface {
    private final BackendSession backendSession;
    private boolean logged = true;  // Will be false
    public UserInterface(BackendSession backendSession) throws BackendException {
        this.backendSession = backendSession;
    }

    public void logIn() throws BackendException {
        if(!this.logged){
            System.out.println("WELCOME in CassCount! \nFirst step is to log into app");

        }

        System.out.println("You are logged as USER "); // <---- Add login functionality and put here name



    }


}
