package backend;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Users {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_USERS;
    private static PreparedStatement INSERT_INTO_USERS;
    private static PreparedStatement DELETE_FROM_USERS;


    private static final String USER_FORMAT = "UserId : %-20s Name: %-10s Pass: %-20s roomId: %-10s\n";

    public Users(Session session) throws BackendException {
        this.session = session;

        prepareStatements();
    }

    private void prepareStatements() throws BackendException {
            try {
                SELECT_ALL_FROM_USERS = session.prepare("SELECT * FROM users;");
            } catch (Exception e) {
                throw new BackendException("Could not prepare statements. " + e.getMessage() + ".", e);
            }
            logger.info("Statements prepared");
        }


    public String selectAllUsers() throws BackendException {
        StringBuilder builder = new StringBuilder();
        BoundStatement bs = new BoundStatement(SELECT_ALL_FROM_USERS);

        ResultSet rs = null;

        try{
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. " + e.getMessage() +".",e);
        }

        for(Row row:rs){
            String userId = row.getUUID("userId").toString();
            String name = row.getString("name");
            String password  = row.getString("password");
            String roomId  = row.getUUID("roomId").toString();

            builder.append(String.format(USER_FORMAT,userId,name,password,roomId));
        }

        return builder.toString();
    }
}
