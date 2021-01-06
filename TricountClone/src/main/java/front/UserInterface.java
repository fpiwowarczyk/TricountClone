package front;

import backend.BackendException;
import backend.BackendSession;
import backend.Room.RoomDTO;
import backend.RoomResult.RoomResultDTO;
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
                    System.out.println("There are no valid choices like: " + choice + "\nPlease chose again\n");
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
        while (logged) {
            System.out.println("Menu:\n1.Show your rooms\n2.Add room\n3.Logout\n4.Select room");
            String choice = scan.nextLine();
            switch (choice) {
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
        System.out.println("-----Showing rooms-----");
        LinkedList<String> userRooms = this.user.getRooms();
        LinkedList<RoomDTO> rooms = new LinkedList<>();
        for (String room : userRooms) {
            rooms.add(backendSession.roomControler.getRoom(room));
        }
        int i = 0;
        for (RoomDTO room : rooms) {
            i++;
            System.out.println(i + "." + room.getName()+"    ID:"+room.getRoomId());
        }
        System.out.println("--------------------");

    }

    public void addRoom() throws BackendException {
        boolean selecting = true;
        while (selecting){
            System.out.println("Menu:\n1.Add new room\n2.Add room from Id \n3.Back");
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    addNewRoom();
                    break;
                case "2":
                    addRoomFromId();
                    break;
                case "3":
                    selecting = false;
                    break;
                default:
                    System.out.println("There are no valid options like:" + choice);
            }
        }


    }

    public void addNewRoom() throws BackendException {
        System.out.print("Room name: ");
        String roomName = scan.nextLine();
        if (!roomName.isEmpty()) {
            RoomDTO roomDTO = new RoomDTO(roomName);
            backendSession.roomControler.addRoom(roomDTO.getName(), roomDTO.getRoomId());
            backendSession.userControler.insertUser(user.getName(), user.getPassword(), roomDTO.getRoomId());
            backendSession.roomResultController.addRoomResult(roomDTO.getRoomId(), user.getUserId(), user.getName(), 0.0);
            user.addRoom(roomDTO.getRoomId());
            System.out.println("A new room \"" + roomDTO.getName() + "\" has been created.");
        }
        System.out.print("----Room added----");
    }

    public void addRoomFromId() throws BackendException {
        System.out.print("Room Id: ");
        String roomId = scan.nextLine();
        if(!roomId.isEmpty()){
            RoomDTO roomDTO = backendSession.roomControler.getRoom(roomId);
            if(roomDTO == null){
                System.out.println("Couldn't find your room");
            } else {
                backendSession.userControler.insertUser(user.getName(),user.getPassword(),roomDTO.getRoomId());
                backendSession.roomResultController.addRoomResult(roomId,user.getUserId(),user.getName(),0.0);
            }
        }
    }

    public void selectRoom() throws BackendException {
        System.out.println("Select the room number\n0.Back");
        showRooms(); //<-nie wiem czy to potrzebne | Mysle ze potrzebne ~FP
        int roomNr = scan.nextInt();
        scan.nextLine();
        if (roomNr != 0) {
            if (roomNr <= user.getRooms().size()) {
                printMenuDetails(roomNr);
            } else {
                System.out.println("Could not find room\n");
            }
        }
    }

    public void printMenuDetails(int roomNr) throws BackendException {
        boolean finish = true;
        while (finish) {
            showDetailsRoom(roomNr); //<-nie jestem pewien właściwości tego wywołania w tym miejscu
            System.out.println("Menu:\n1.Add payment\n2.Add new user\n3.Show payment history\n4.Reimburse\n5.Back");
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    //addPayment();
                    break;
                case "2":
                    //Add new user
                    break;
                case "3":
                    //showPaymentHistory();
                    break;
                case "4":
                    //Reimburse();
                    break;
                case "5":
                    finish = false; //<-czy to powinno wracać do wyboru pokoju czy do głównego menu? | Moim zdaniem powinno sie wracac tam gdzie bylo sie poprezednio ~FP
                    break;
                default:
                    System.out.println("There is no option like that, chose again");
            }
        }
    }

    public void showDetailsRoom(int roomNr) throws BackendException {
        System.out.println("Selecting room nr" + (roomNr - 1));
        LinkedList<RoomResultDTO> roomResultDTOS = backendSession.roomResultController.getRoomResults(user.getRooms().get(roomNr - 1));
        String roomName = backendSession.roomControler.getRoom(roomResultDTOS.get(0).getRoomId()).getName();
        System.out.println("Room details: " + roomName);
        for (RoomResultDTO room : roomResultDTOS) {
            System.out.println("User: " + room.getUserName() + " " + room.getMoney());
        }
    }

}