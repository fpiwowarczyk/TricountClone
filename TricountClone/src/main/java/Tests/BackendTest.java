package Tests;

import backend.BackendException;
import backend.BackendSession;
import backend.User.UserControler;
import backend.User.UserDTO;

import java.util.LinkedList;
import java.util.UUID;

public class BackendTest {

    public static void runAllBackendTests(BackendSession backendSession) throws BackendException {
        userTests(backendSession);
    }

    public static void userTests(BackendSession backendSession) throws BackendException {

        System.out.println("Get Users");
        System.out.println(backendSession.userControler.getUsers());

        System.out.println("Insert User");
        backendSession.userControler.insertUser("Test","User");

        System.out.println(backendSession.userControler.getUsers());

        System.out.println("Get User");
        LinkedList<UserDTO>  result = backendSession.userControler.getUser("Test","User");

        UserDTO test = result.get(0);
        System.out.println("Delete User");
        backendSession.userControler.deleteUser(test.getName(),test.getPassword(),test.getUserId());

        System.out.println(backendSession.userControler.getUsers());

    }
}
