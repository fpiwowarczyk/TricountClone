package Tests;

import backend.BackendException;
import backend.BackendSession;
import backend.Room.RoomDTO;
import backend.User.UserControler;
import backend.User.UserDTO;

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
            System.out.println(backendSession.paymentControler.getAllPayments());
        }
        backendSession.paymentControler.getAllPayments();

        if(printTest){
            System.out.println("Get Payments by room: ab036100-3b36-11eb-adc1-0242ac120003 \n");
            System.out.println(backendSession.paymentControler.getPaymentsByRoom("ab036100-3b36-11eb-adc1-0242ac120003"));
        }
        backendSession.paymentControler.getPaymentsByRoom("ab036100-3b36-11eb-adc1-0242ac120003");

        if(printTest){
            System.out.println("Get Payments by room: ab036100-3b36-11eb-adc1-0242ac120003 and payer: ab036999-3b36-11eb-adc1-0242ac120004");
            System.out.println(backendSession.paymentControler.getPaymentByRoomAndPayer("ab036100-3b36-11eb-adc1-0242ac120003","ab036999-3b36-11eb-adc1-0242ac120004"));
        }
        backendSession.paymentControler.getPaymentByRoomAndPayer("ab036100-3b36-11eb-adc1-0242ac120003","ab036999-3b36-11eb-adc1-0242ac120004");

        if(printTest){
            System.out.println("Get Payments by room: ab036100-3b36-11eb-adc1-0242ac120003 and payer: ab036999-3b36-11eb-adc1-0242ac120004 and paymentId:ab036100-3b36-11eb-adc1-0242ac120011 ");
            System.out.println(backendSession.paymentControler.getSinglePayment("ab036100-3b36-11eb-adc1-0242ac120003","ab036999-3b36-11eb-adc1-0242ac120004","ab036100-3b36-11eb-adc1-0242ac120011"));
        }
        backendSession.paymentControler.getSinglePayment("ab036100-3b36-11eb-adc1-0242ac120003","ab036999-3b36-11eb-adc1-0242ac120004","ab036100-3b36-11eb-adc1-0242ac120011");

        if(printTest){
            System.out.println("Add payment");
        }
        backendSession.paymentControler.addPayment("ab036100-3b36-11eb-adc1-0242ac120666",666.0,"ab036999-3b36-11eb-adc1-0242ac120004","ab036100-3b36-11eb-adc1-0242ac120123");
        backendSession.paymentControler.addPayment("ab036100-3b36-11eb-adc1-0242ac120666",666.0,"ab036999-3b36-11eb-adc1-0242ac120004","ab036100-3b36-11eb-adc1-0242ac120123");

        if(printTest){
            System.out.println("Get Payments");
            System.out.println(backendSession.paymentControler.getAllPayments());
        }

        if(printTest){
            System.out.println("Delete Payments by Room: ab036100-3b36-11eb-adc1-0242ac120666");
        }
        backendSession.paymentControler.deletePaymentsByRoom("ab036100-3b36-11eb-adc1-0242ac120666");

        if(printTest){
            System.out.println("Get Payments");
            System.out.println(backendSession.paymentControler.getAllPayments());
        }

        backendSession.paymentControler.addPayment("ab036100-3b36-11eb-adc1-0242ac120666",666.0,"ab036999-3b36-11eb-adc1-0242ac120004","ab036100-3b36-11eb-adc1-0242ac120123");
        backendSession.paymentControler.addPayment("ab036100-3b36-11eb-adc1-0242ac120666",666.0,"ab036999-3b36-11eb-adc1-0242ac120555","ab036100-3b36-11eb-adc1-0242ac120123");
        backendSession.paymentControler.addPayment("ab036100-3b36-11eb-adc1-0242ac120666",666.0,"ab036999-3b36-11eb-adc1-0242ac120555","ab036100-3b36-11eb-adc1-0242ac120123");
        backendSession.paymentControler.addPayment("ab036100-3b36-11eb-adc1-0242ac120666",666.0,"ab036999-3b36-11eb-adc1-0242ac120666","ab036100-3b36-11eb-adc1-0242ac120123");
        backendSession.paymentControler.addPayment("ab036100-3b36-11eb-adc1-0242ac120666",666.0,"ab036999-3b36-11eb-adc1-0242ac120666","ab036100-3b36-11eb-adc1-0242ac120123");

        if(printTest){
            System.out.println("Delete Payments by Room: ab036100-3b36-11eb-adc1-0242ac120666 and User: ab036999-3b36-11eb-adc1-0242ac120666");
        }

        backendSession.paymentControler.deletePaymentsByRoomAndPayer("ab036100-3b36-11eb-adc1-0242ac120666","ab036999-3b36-11eb-adc1-0242ac120666");




        if(printTest){
            System.out.println("Get Payments");
            System.out.println(backendSession.paymentControler.getAllPayments());
        }

        System.out.println("Done test for payments");
    }
}
