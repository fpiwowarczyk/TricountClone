import backend.BackendException;
import backend.BackendSession;
import front.UserInterface;

import java.io.IOException;
import java.util.Properties;


public class Main {

    private static final String PROPERTIES_FILENAME = "config.properties";

    public static void main(String[] args) throws BackendException {
        String contactPoint = null;
        String keyspace = null;
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME));

            contactPoint = properties.getProperty("contact_point");
            keyspace = properties.getProperty("keyspace");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BackendSession backendSession = new BackendSession(contactPoint, keyspace);


        UserInterface UI = new UserInterface(backendSession);

        UI.start();

        backendSession.endSession();
    }
}

// Whats Missing #TODO
//
//
//
//
//


// #TODO Jakie problemy sie pojawily
// Jak dodawalem usera to nie lapalo ze go dodaje z jakiegos powodu. Rozwiazanie trzeba bylo lepiej okreslic pokoj tzn <keyspace>.table a nie tylko table przy insercie