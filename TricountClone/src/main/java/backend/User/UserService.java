package backend.User;

import backend.BackendException;
import com.datastax.driver.core.*;

import java.util.LinkedList;
import java.util.UUID;

public class UserService {

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_USERS;
    private static PreparedStatement SELECT_USER_BY_NAME_AND_PASS;
    private static PreparedStatement SELECT_USER_BY_ID;
    private static PreparedStatement INSERT_USER;
    private static PreparedStatement DELETE_USER;
    private static PreparedStatement DELETE_USER_ROOM;


    private static final String USER_FORMAT = "UserId : %-20s Name: %-10s Pass: %-20s roomId: %-10s\n";

    public UserService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();
    }

    private void prepareStatements() throws BackendException {
        try {
            SELECT_ALL_FROM_USERS = session.prepare("SELECT * FROM users;");
            SELECT_USER_BY_NAME_AND_PASS = session.prepare("SELECT * FROM users WHERE name=? AND password =?;");
            SELECT_USER_BY_ID = session.prepare("SELECT * FROM users WHERE name = ? AND password = ? AND userId = ?");
            INSERT_USER = session.prepare("INSERT INTO tricount.users (name,password,userId,roomId) VALUES (?,?,?,?);");
            DELETE_USER = session.prepare("DELETE FROM users WHERE name=? AND password=? AND userID =?;");
            DELETE_USER_ROOM = session.prepare("DELETE FROM users WHERE name=? AND password=? AND userID =? AND roomId = ?;");
        } catch (Exception e) {
            throw new BackendException("Could not prepare statements. " + e.getMessage() + ".", e);
        }
    }


    public LinkedList<UserDTO> selectAllUsers() throws BackendException {
        LinkedList<UserDTO> users = new LinkedList<>();
        BoundStatement bs = new BoundStatement(SELECT_ALL_FROM_USERS);

        ResultSet rs = null;

        try {
            rs = session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }

        for (Row row : rs) {
            String userId = row.getUUID("userId").toString();
            String name = row.getString("name");
            String password = row.getString("password");
            String roomId = row.getUUID("roomId").toString();

            users.add(new UserDTO(name, password, userId, roomId));
        }
        return users;
    }

    public UserDTO selectUserById(String nameInput, String passwordInput, String userIdInput) throws BackendException {
        ResultSet rs = null;
        BoundStatement bs = new BoundStatement(SELECT_USER_BY_ID);


        try {
            bs.bind(nameInput, passwordInput, UUID.fromString(userIdInput));
            rs = session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }
        String userId = rs.one().getUUID("userID").toString();
        String name = rs.one().getString("name");
        String password = rs.one().getString("password");
        String roomId = rs.one().getUUID("roomId").toString();
        return new UserDTO(name, password, userId, roomId);
    }

    public LinkedList<UserDTO> selectUserByNameAndPassword(String name, String password) throws BackendException {
        ResultSet rs = null;
        BoundStatement bs = new BoundStatement(SELECT_USER_BY_NAME_AND_PASS);

        LinkedList<UserDTO> user = new LinkedList<>();

        try {
            bs.bind(name, password);
            rs = session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }
        for (Row row : rs) {
            String userId = row.getUUID("userId").toString();
            String roomId = row.getUUID("roomId").toString();

            if (userId != null && roomId != null) {
                user.add(new UserDTO(name, password, userId, roomId));
            }
        }
        return user;
    }

    public void deleteUser(String name, String password, String userId) throws BackendException {
        BoundStatement bs = new BoundStatement(DELETE_USER);

        try {
            bs.bind(name, password, UUID.fromString(userId));
            session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }
    }

    public void deleteUserRoom(String name, String password, String userId, String roomId) throws BackendException {
        BoundStatement bs = new BoundStatement(DELETE_USER_ROOM);

        try {
            bs.bind(name, password, UUID.fromString(userId),UUID.fromString(roomId));
            session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }
    }

    public void insertUser(String name, String password, String roomId) throws BackendException {
        BoundStatement insertUser = new BoundStatement(INSERT_USER);
        BoundStatement selectUser = new BoundStatement(SELECT_USER_BY_NAME_AND_PASS);
        ResultSet rs = null;
        UserDTO user = new UserDTO(name, password, roomId);

        try {
            selectUser.bind(user.getName(), user.getPassword());
            rs = session.execute(selectUser);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }
        try {
            if (rs.iterator().hasNext()) {
                String userUUID = rs.one().getUUID("userId").toString();
                insertUser.bind(user.getName(), user.getPassword(), UUID.fromString(userUUID), UUID.fromString(user.getRoomId()));
            } else {
                insertUser.bind(user.getName(), user.getPassword(), UUID.randomUUID(), UUID.fromString(user.getRoomId()));
            }
            session.execute(insertUser);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }
    }

}



