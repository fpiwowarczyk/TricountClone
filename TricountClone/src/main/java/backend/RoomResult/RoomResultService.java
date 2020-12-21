package backend.RoomResult;

import backend.BackendException;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;


public class RoomResultService {

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_RESULT;

    public RoomResultService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();
    }

    private void prepareStatements() throws BackendException {
        try{
            SELECT_ALL_FROM_RESULT = session.prepare("SELECT * FROM roomresult");


        }catch (Exception e){
            throw new BackendException("Could not prepare statements. " + e.getMessage()+".",e);
        }
    }

}
