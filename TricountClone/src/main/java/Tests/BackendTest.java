package Tests;

import backend.BackendException;
import backend.BackendSession;
import backend.Room.RoomDTO;
import backend.User.UserControler;
import backend.User.UserDTO;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.LinkedList;
import java.util.UUID;

public class BackendTest {

    public static void runAllBackendTests(BackendSession backendSession,Boolean printTest) throws BackendException {
        userTests(backendSession, printTest);
        roomTests(backendSession,printTest);
        paymentTests(backendSession,printTest);

    }

    public static void userTests(BackendSession backendSession, Boolean printTest) throws BackendException {
        System.out.println("Begin test for user");

        if(printTest){
            System.out.println("Get Users");
            System.out.println(backendSession.userControler.getUsers());
        }
        backendSession.userControler.getUsers();

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
        System.out.println("Done tests for user");
    }

    public static void roomTests(BackendSession backendSession, Boolean printTest) throws BackendException {
        System.out.println("Begin test for room");

        String TEST_ROOM = "RoomTest";
        if(printTest){
            System.out.println("Get Rooms");
            System.out.println(backendSession.roomControler.getRooms());
        }
        backendSession.roomControler.getRooms();

        if(printTest){
            System.out.println("Insert Room");
        }
        backendSession.roomControler.addRoom(TEST_ROOM);

        if(printTest){
            System.out.println("Get Room");
        }
        RoomDTO room =backendSession.roomControler.getRoom(TEST_ROOM);

        if(printTest){
            System.out.println("RoomId: "+room.getRoomId()+" Name: "+room.getName()+"\n");
        }

        if(printTest){
            System.out.println("Get Rooms ");
            System.out.println(backendSession.roomControler.getRooms());
        }

        if(printTest){
            System.out.println("Delete Room"+room.getName()+" with ID:"+room.getRoomId());
        }
        backendSession.roomControler.deleteRoom(room.getName(),room.getRoomId());


        if(printTest){
            System.out.println("Get Rooms");
            System.out.println(backendSession.roomControler.getRooms());
        }

        System.out.println("Done for room");
    }

    public static void paymentTests(BackendSession backendSession,Boolean printTest) throws BackendException {
        System.out.println("Begin test for payments");
        if(printTest){
            System.out.println("Get Payments");
            System.out.println(backendSession.paymentControler.getPayments());
        }
        backendSession.paymentControler.getPayments();



        System.out.println("Done test for payments");
    }
}
