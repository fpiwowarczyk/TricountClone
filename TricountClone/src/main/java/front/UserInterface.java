package front;

import backend.BackendException;
import backend.BackendSession;
import backend.Room.RoomDTO;
import backend.User.UserDTO;


import java.util.LinkedList;
import java.util.Scanner;

public class UserInterface {
    private final BackendSession backendSession;
    private User user;
    private boolean logged = false;  // Will be false
    public UserInterface(BackendSession backendSession) throws BackendException {
        this.backendSession = backendSession;
    }

    public void logIn() throws BackendException {
        try {
            while (!logged) {
                System.out.println("WELCOME in CassCount! \nFirst step is to log into app");
                Scanner scan = new Scanner(System.in);
                System.out.print("Login: ");
                String name = scan.nextLine();
                System.out.print("Password: ");
                String password = scan.nextLine();
                if(!name.isEmpty() && !password.isEmpty()) {
                    LinkedList<UserDTO> users = backendSession.userControler.getUser(name, password);
                    if (users.size() > 0) {
                        logged = true;
                        user = new User(name, password, users.getFirst().getUserId(), new LinkedList<>());
                        for (UserDTO userData : users) {
                            user.addRoom(userData.getRoomId());
                        }
                        System.out.println("You are logged as " + name);
                    } else {
                        System.out.println("Login fail");
                    }
                }else {
                System.out.println("Login fail");
                }
            }
        }catch (Exception e){
            throw new BackendException("Login error. " + e.getMessage() + ".", e);
        }
    }

    /*public void showUserRooms(User user) throws BackendException {
        LinkedList<RoomDTO> rooms = new LinkedList<>();
        System.out.println("Your rooms: ");
        for(String room : user.getRooms()){
            rooms.add(backendSession.roomControler.getRoom(room));
        }
        for(int i=0; i<rooms.size(); i++){
            System.out.println(i+1+". "+rooms.get(i));
        }
    }*/
}
