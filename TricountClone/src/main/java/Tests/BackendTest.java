package Tests;

import backend.BackendException;
import backend.BackendSession;
import backend.User.UserControler;
import backend.User.UserDTO;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.LinkedList;
import java.util.UUID;

public class BackendTest {

    public static void runAllBackendTests(BackendSession backendSession,Boolean printTest) throws BackendException {
        userTests(backendSession, printTest);
        roomTests(backendSession,printTest);

    }

    public static void userTests(BackendSession backendSession, Boolean printTest) throws BackendException {

        if(printTest){
            System.out.println("Get Users");
            System.out.println(backendSession.userControler.getUsers());
        }

        if(printTest){
            System.out.println("Insert User");
        }

        backendSession.userControler.insertUser("Test","User");
        if(printTest){
            System.out.println(backendSession.userControler.getUsers());
            System.out.println("Get User");
        }
        LinkedList<UserDTO>  result = backendSession.userControler.getUser("Test","User");

        UserDTO test = result.get(0);
        if(printTest){
            System.out.println("Delete User");
        }
        backendSession.userControler.deleteUser(test.getName(),test.getPassword(),test.getUserId());
        if(printTest){
            System.out.println(backendSession.userControler.getUsers());
        }
    }
    public static void roomTests(BackendSession backendSession, Boolean printTest) throws BackendException {
        if(printTest){
            System.out.println("Get Rooms");
            System.out.println(backendSession.roomControler.getRooms());
        }


    }
}
