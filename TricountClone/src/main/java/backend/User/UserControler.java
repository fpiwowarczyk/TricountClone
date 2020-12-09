package backend.User;

import backend.BackendException;
import com.datastax.driver.core.Session;

import java.util.LinkedList;

public class UserControler{

    UserService userService;

    public UserControler(Session session) throws BackendException {
        userService = new UserService(session);
    }
    public String getUsers() throws BackendException {
        return userService.selectAllUsers();
    }

    public LinkedList<UserDTO> getUser(String name, String password) throws BackendException {
        return userService.selectUserByNameAndPassword(name,password);
    }

    public void insertUser(String name, String password) throws BackendException {
        userService.insertUser(name,password);
    }

    public void deleteUser(String name, String password, String userId) throws BackendException {
        userService.deleteUser(name,password,userId);
    }

}
