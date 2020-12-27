import backend.BackendException;
import backend.BackendSession;
import front.UserInterface;

import java.io.IOException;
import java.util.Properties;



public class Main {

	private static final String PROPERTIES_FILENAME = "config.properties";

	public static void main(String[] args) throws BackendException, InterruptedException {
		String contactPoint = null;
		String keyspace = null;
		Properties properties = new Properties();
		try {
			properties.load(Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME));

			contactPoint = properties.getProperty("contact_point");
			keyspace = properties.getProperty("keyspace");
		} catch (IOException ex){
			ex.printStackTrace();
		}

		BackendSession backendSession = new BackendSession(contactPoint, keyspace);


		UserInterface UI = new UserInterface(backendSession);

		backendSession.endSession();



	}
}
