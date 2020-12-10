package backend.User;

import backend.BackendException;
import com.datastax.driver.core.*;
import org.apache.cassandra.cql3.statements.Bound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.UUID;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_USERS;
    private static PreparedStatement SELECT_USER_BY_NAME_AND_PASS;
    private static PreparedStatement INSERT_USER;
    private static PreparedStatement DELETE_USER;


    private static final String USER_FORMAT = "UserId : %-20s Name: %-10s Pass: %-20s roomId: %-10s\n";

    public UserService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();
    }

    private void prepareStatements() throws BackendException {
            try {
                SELECT_ALL_FROM_USERS = session.prepare("SELECT * FROM users;");
                SELECT_USER_BY_NAME_AND_PASS = session.prepare("SELECT * FROM users WHERE name=? AND password =?");
                INSERT_USER = session.prepare("INSERT INTO users (name,password,userId,roomId) VALUES (?,?,?,?)");
                DELETE_USER = session.prepare("DELETE FROM users WHERE name=? AND password=? AND userID =?");
            } catch (Exception e) {
                throw new BackendException("Could not prepare statements. " + e.getMessage() + ".", e);
            }
            logger.info("Statements prepared for UserService");
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

    public LinkedList<UserDTO> selectUserByNameAndPassword(String name, String password) throws BackendException{
        ResultSet rs = null;
        BoundStatement bs = new BoundStatement(SELECT_USER_BY_NAME_AND_PASS);

        LinkedList<UserDTO> result = new LinkedList<UserDTO>();

        try{
            bs.bind(name,password);
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. " + e.getMessage() +".",e);
        }

        for(Row row:rs){
            String userId = row.getUUID("userId").toString();
            String roomId  = row.getUUID("roomId").toString();

            if(userId != null && roomId != null){
                result.add(new UserDTO(name,password,userId,roomId));
            }

        }

        return result;
    }

    public void deleteUser(String name, String password, String userId) throws BackendException {
        BoundStatement bs = new BoundStatement(DELETE_USER);

        try{
            bs.bind(name,password,UUID.fromString(userId));
            session.execute(bs);
        } catch  (Exception e){
            throw new BackendException("Could not perform a query. " + e.getMessage() +".",e);
        }
    }

    public void insertUser(String name, String password) throws BackendException {
        BoundStatement bs = new BoundStatement(INSERT_USER);

        UUID userID = UUID.randomUUID();
        UUID roomID = UUID.randomUUID(); // #TODO  Change it

        try{
            bs.bind(name,password,userID,roomID);
            session.execute(bs);
        } catch  (Exception e){
            throw new BackendException("Could not perform a query. " + e.getMessage() +".",e);
        }

    }


}



