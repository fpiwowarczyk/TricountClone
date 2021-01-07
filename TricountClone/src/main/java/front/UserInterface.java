package front;

import backend.BackendException;
import backend.BackendSession;
import backend.Room.RoomDTO;
import backend.RoomResult.RoomResultDTO;
import backend.User.UserDTO;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;


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
            backendSession.roomResultController.addRoomResult(testRoom.getRoomId(),backendSession.userControler.getUser(name,password).get(0).getUserId(), name, 0.0);

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
            System.out.println(i + "." + room.getName() + "    ID:" + room.getRoomId());
        }
        System.out.println("--------------------");

    }

    public void addRoom() throws BackendException {
        boolean selecting = true;
        while (selecting) {
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
        if (!roomId.isEmpty()) {
            RoomDTO roomDTO = backendSession.roomControler.getRoom(roomId);
            System.out.println(roomDTO.getName());
            if (roomDTO.isEmpty()) {
                System.out.println("Couldn't find your room");
            } else {
                backendSession.userControler.insertUser(user.getName(), user.getPassword(), roomDTO.getRoomId());
                backendSession.roomResultController.addRoomResult(roomId, user.getUserId(), user.getName(), 0.0);
                mapUser(this.user.getName(),this.user.getPassword());
            }
        }
    }

    public void selectRoom() throws BackendException {
        boolean finish = true;
        while (finish) {
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
            }else {
                finish = false;
            }
        }
    }

    public void printMenuDetails(int roomNr) throws BackendException {
        boolean finish = true;
        RoomDTO room;
        while (finish) {
            showDetailsRoom(roomNr);
            System.out.println("Menu:\n1.Add payment\n2.Leave room\n3.Back");
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    addPayment(roomNr);
                    break;
                case "2":
                    finish = leaveRoom(roomNr);
                    break;
                case "3":
                    finish = false;
                    break;
                default:
                    System.out.println("There is no option like that, chose again");
            }
        }
    }

    public void addPayment(int roomNr) throws BackendException {
        boolean paying = true;
        while (paying) {
            System.out.println("Select what are you doing:\n1.Pay for one user\n2.Pay for everyone in room\n3.Back");
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    payForOne(roomNr);
                    break;
                case "2":
                    payForEveryone(roomNr);
                    break;
                case "3":
                    paying = false;
                    break;
                default:
                    System.out.println("There are no valid options like: " + choice);
            }
        }
    }

    public void payForOne(int roomNr) throws BackendException {
        LinkedList<RoomResultDTO> roomResultDTOS = backendSession.roomResultController.getRoomResults(user.getRooms().get(roomNr - 1));
        boolean choosing = true;
        String receiver = "";
        int i = 0;
        while (choosing) {
            i = 0;
            System.out.println("Chose for whom are you paying for");
            for (RoomResultDTO roomResult : roomResultDTOS) {
                i++;
                System.out.println(i + "." + roomResult.getUserName());
            }
            receiver = scan.nextLine();
            if (Integer.parseInt(receiver) <= i && Integer.parseInt(receiver) > 0) {
                choosing = false;
            } else {
                System.out.println("Please chose valid option");
            }
        }
        System.out.println("How much did you pay?     If he paid for you start with - sign");
        String inputMoney = scan.nextLine();
        Double money = Double.parseDouble(inputMoney);
        Double startValue = roomResultDTOS.get(i - 1).getMoney();
        Double endValue = startValue - money;
        roomResultDTOS.get(Integer.parseInt(receiver) - 1).setMoney(endValue);
        for (RoomResultDTO roomResult : roomResultDTOS) {
            if (roomResult.getUserId().equals(this.user.getUserId())) {
                startValue = roomResult.getMoney();
                endValue = startValue + money;
                roomResult.setMoney(endValue);
            }
        }
        updateRoomResults(roomResultDTOS);
    }

    public void payForEveryone(int roomNr) throws BackendException {
        LinkedList<RoomResultDTO> roomResultDTOS = backendSession.roomResultController.getRoomResults(user.getRooms().get(roomNr - 1));
        System.out.println("WARNING: IF YOU ARE PAYING FOR EVEYONE YOU PAY ALSO FOR YOURSELF\n" +
                "How much did you pay?     If he paid for you start with - sign");
        String inputMoney = scan.nextLine();
        Double money = Double.parseDouble(inputMoney);
        Double splitedMoney = money / roomResultDTOS.size();
        for (RoomResultDTO roomResult : roomResultDTOS) {
            Double startValue = roomResult.getMoney();
            Double endValue = startValue - splitedMoney;
            roomResult.setMoney(endValue);
            if (roomResult.getUserId().equals(this.user.getUserId())) {
                startValue = roomResult.getMoney();
                endValue = startValue + money;
                roomResult.setMoney(endValue);
            }
        }

        updateRoomResults(roomResultDTOS);
    }

    public void updateRoomResults(LinkedList<RoomResultDTO> roomResultDTOS) throws BackendException {
        for (RoomResultDTO roomResult : roomResultDTOS) {
            backendSession.roomResultController.addRoomResult(roomResult.getRoomId(), roomResult.getUserId(), roomResult.getUserName(), roomResult.getMoney());
        }
    }

    public void showDetailsRoom(int roomNr) throws BackendException {
        System.out.println("Selecting room nr: " + (roomNr));
        String roomUUID = user.getRooms().get(roomNr-1);
        LinkedList<RoomResultDTO> roomResultDTOS = backendSession.roomResultController.getRoomResults(roomUUID);
        String roomName = backendSession.roomControler.getRoom(roomUUID).getName();
        System.out.println("Room details: " + roomName);
        for (RoomResultDTO room : roomResultDTOS) {
            System.out.println("User: " + room.getUserName() + " " + room.getMoney());
        }
    }

    public boolean leaveRoom(int roomNr) throws BackendException {
        LinkedList<RoomResultDTO> roomResultDTO = backendSession.roomResultController.getRoomResults(user.getRooms().get(roomNr - 1));
        String roomName = backendSession.roomControler.getRoom(roomResultDTO.get(0).getRoomId()).getName();
        if (roomResultDTO.size() == 1) {
            backendSession.roomControler.deleteRoom(roomName,roomResultDTO.get(0).getRoomId());
            backendSession.roomResultController.deleteRoomResult(roomResultDTO.get(0).getRoomId());
            backendSession.userControler.deleteUserRoom(user.getName(),user.getPassword(),user.getUserId(),roomResultDTO.get(0).getRoomId());
            user.getRooms().remove(roomNr-1);
            System.out.println("You left room");
            return false;
        }
        if (roomResultDTO.size() > 1) {
            for (RoomResultDTO room : roomResultDTO) {
                if (user.getUserId().equals(room.getUserId())) {
                    if (room.getMoney() == 0.0) {
                        backendSession.roomResultController.deleteUserRoomResult(roomResultDTO.get(0).getRoomId(),user.getUserId());
                        backendSession.userControler.deleteUserRoom(user.getName(),user.getPassword(),user.getUserId(),roomResultDTO.get(0).getRoomId());
                        user.getRooms().remove(roomNr-1);
                        System.out.println("You left room");
                        return false;
                    } else {
                        System.out.println("You can not leave the room");
                        return true;
                    }
                }

            }

        }
        return true;
    }
}