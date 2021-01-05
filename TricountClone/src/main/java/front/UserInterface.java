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
    private boolean logged = false;
    private final Scanner scan = new Scanner(System.in);

    public UserInterface(BackendSession backendSession) throws BackendException {
        this.backendSession = backendSession;
    }

    public void start() throws BackendException {
        choseLogOrRegister();
    }

    private void choseLogOrRegister() throws BackendException {
        System.out.println("WELCOME in CassCount! \n");
        boolean finish = true;
        while (finish) {
            System.out.println("Select option: \n1.LogIn\n2.Register\n3.Quit");
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    logIn();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    finish = false;
                    break;
                default:
                    System.out.println("There are not valid choices like: " + choice + "\nPlease chose again\n");
            }
        }
    }

    public void register() throws BackendException {
        System.out.print("Login: ");
        String name = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();
        if (!name.isEmpty() && !password.isEmpty()) {
            System.out.println("You have no rooms so we will add you test room");
            RoomDTO testRoom = new RoomDTO("TestRoom");
            backendSession.roomControler.addRoom(testRoom.getName(), testRoom.getRoomId());
            backendSession.userControler.insertUser(name, password, testRoom.getRoomId());
        }
    }

    public void logIn() throws BackendException {
        System.out.print("Login: ");
        String name = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();
        if (!name.isEmpty() && !password.isEmpty()) {
            if (mapUser(name, password)) {
                System.out.println("You are logged as " + this.user.getName());
                this.logged = true;
                printMenu();
            } else {
                System.out.println("Could not find user");
            }
        }
    }

    public boolean mapUser(String name, String password) throws BackendException {
        LinkedList<UserDTO> users = backendSession.userControler.getUser(name, password);
        if (users.isEmpty()) {
            return false;
        } else {
            UserDTO firstUser = users.get(0);
            this.user = new User(firstUser.getName(), firstUser.getPassword(), firstUser.getUserId());
            users.forEach(e -> {
                String roomId = e.getRoomId();
                this.user.addRoom(roomId);
            });
            return true;
        }
    }

    public void printMenu() throws BackendException {
        while(logged){
            System.out.println("Menu:\n1.Show your rooms\n2.Add room\n3.Logout\n4.Select room");
            String choice = scan.nextLine();
            switch (choice){
                case "1":
                    showRooms();
                    break;
                case "2":
                    addRoom();
                    break;
                case "3":
                    logged = false;
                    break;
                case "4":
                    selectRoom();
                    break;
                default:
                    System.out.println("There is no option like that, chose again");
            }
        }
    }

    public void showRooms() throws BackendException {
        LinkedList<String> userRooms = this.user.getRooms();
        LinkedList<RoomDTO> rooms = new LinkedList<>();
        for(String room: userRooms){
            rooms.add(backendSession.roomControler.getRoom(room));
        }
        int i =0;
        for(RoomDTO room: rooms){
            i++;
            System.out.println(i+"."+room.getName());
        }
        System.out.println("Showing rooms");
    }

    public void addRoom() throws BackendException{
        System.out.println("Adding room");
        System.out.print("Room name: ");
        String roomName = scan.nextLine();
        if(!roomName.isEmpty()){
            RoomDTO roomDTO = new RoomDTO(roomName);
            backendSession.roomControler.addRoom(roomDTO.getName(),roomDTO.getRoomId());
            backendSession.userControler.insertUser(user.getName(),user.getPassword(),roomDTO.getRoomId());
            user.addRoom(roomDTO.getRoomId());
            System.out.println("A new room \""+roomDTO.getName()+"\" has been created.");
        }
    }

    public void selectRoom() throws BackendException{
        System.out.println("Select the room number\n0.Back");
        showRooms();
        int roomNr = scan.nextInt();
        scan.nextLine();
        if(roomNr != 0) {
            if (roomNr <= user.getRooms().size()) {
                //showDetailsRoom(roomNr-1)<- metoda zwracająca szczegóły wybranego pokoju/lista pokoi z showRooms()
                //                            mogłaby być globalna i z niej można by wyciągać roomid pokoju o podanym indexie.
            }else {
                System.out.println("Could not find room\n");
            }
        }
    }
}