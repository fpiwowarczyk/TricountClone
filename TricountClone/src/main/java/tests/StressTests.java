package tests;

import backend.BackendException;
import backend.BackendSession;
import backend.Room.RoomDTO;
import backend.RoomResult.RoomResultDTO;
import backend.User.UserDTO;

import org.apache.commons.math3.analysis.integration.RombergIntegrator;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// #WNIOSKI
// Najlepiej dziala dla 10 nodeow oraz 100 ms opoznienia
// Dla 3 nodeow i 100 ms opozenia raczej dziala
// COnsistency im wieksze tym wiecej bledow najlepiej dla ONE
// Dla 30 nodeow nie bylo juz widac poprawy miedzy dzialaniem dla 30, a dla 10
// Jedyne co dla 30 nodeow nie trzeba dodawac opoznienia, alemoze to wynikac z tego ze komputer zaczyna dzialac bardzo wolno
public class StressTests {
    private final BackendSession backendSession;

    public StressTests(BackendSession backendSession) throws BackendException {
        this.backendSession = backendSession;
    }

    class payTest extends Thread {
        private BackendSession backendSession;
        private LinkedList<UserDTO> userList;
        private LinkedList<RoomResultDTO> roomResultDTOS;
        private int user;
        Random r = new Random();

        public payTest(BackendSession backendSession, int user, LinkedList<UserDTO> userList) {
            this.backendSession = backendSession;
            this.userList = userList;
            this.user = user;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {

                double myMoney = 0.0;
                double receiverMoney = 0.0;
                //double money = Math.round((r.nextDouble() * 200.0 - 100.0)*100.0)/100.0;
                double money = 1 * Math.random() > 0.5 ? 1 : -1;
                int receiver = 0;
                boolean finish = true;
                while (finish) {
                    receiver = r.nextInt(5);
                    if (receiver != user) {
                        finish = false;
                    }
                }
                try {

                    roomResultDTOS = backendSession.roomResultController.getRoomResults(userList.get(0).getRoomId());
                    for (RoomResultDTO roomResult : roomResultDTOS) {
                        if (roomResult.getUserId().equals(userList.get(user).getUserId())) {
                            Thread.sleep(100);
                            backendSession.roomResultController.addRoomResult(roomResult.getRoomId(), roomResult.getUserId(), roomResult.getUserName(), (backendSession.roomResultController.getUserRoomResults(roomResult.getRoomId(), roomResult.getUserId()).getMoney() + money));
                        }
                    }
                    for (RoomResultDTO roomResult : roomResultDTOS) {
                        if (roomResult.getUserId().equals(userList.get(receiver).getUserId())) {
                            Thread.sleep(100);
                            backendSession.roomResultController.addRoomResult(roomResult.getRoomId(), roomResult.getUserId(), roomResult.getUserName(), (backendSession.roomResultController.getUserRoomResults(roomResult.getRoomId(), roomResult.getUserId()).getMoney() - money));
                        }
                    }
                    checkRoom(i);
                } catch (BackendException | InterruptedException e) {
                    System.out.println("Could not finish tests");
                }
            }
        }
    }

    public void startTest() throws BackendException, InterruptedException {
        LinkedList<UserDTO> userList = backendSession.userControler.getUsers();
        payTest p1 = new payTest(backendSession, 0, userList);
        payTest p2 = new payTest(backendSession, 1, userList);
        payTest p3 = new payTest(backendSession, 2, userList);
        payTest p4 = new payTest(backendSession, 3, userList);
        payTest p5 = new payTest(backendSession, 4, userList);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p1.join();
        p2.join();
        p3.join();
        p4.join();
        p5.join();
    }

    public void checkSum() throws BackendException, InterruptedException {
        double sum = 0.0;
        LinkedList<UserDTO> userList = backendSession.userControler.getUsers();
        LinkedList<RoomResultDTO> roomResultDTOS = backendSession.roomResultController.getRoomResults(userList.get(0).getRoomId());
        for (RoomResultDTO roomResult : roomResultDTOS) {
            sum += roomResult.getMoney();
        }
        System.out.println("=================================");
        System.out.println("FINAL RESULT SUM OF ROOM IS: " + sum);
        System.out.println("=================================");
    }

    public void checkRoom(Integer i) throws BackendException {
        double sum = 0.0;
        LinkedList<UserDTO> userList = backendSession.userControler.getUsers();
        LinkedList<RoomResultDTO> roomResultDTOS = backendSession.roomResultController.getRoomResults(userList.get(0).getRoomId());
        System.out.println("Iteration " + i + "-------------------------");
        for (RoomResultDTO roomResult : roomResultDTOS) {
            System.out.println("User " + roomResult.getUserName() + " have " + roomResult.getMoney() + " $");
        }
    }

    public void cleanTransactions() throws BackendException {
        LinkedList<UserDTO> userList = backendSession.userControler.getUsers();
        LinkedList<RoomResultDTO> roomResultDTOS = backendSession.roomResultController.getRoomResults(userList.get(0).getRoomId());
        for (RoomResultDTO roomResult : roomResultDTOS) {
            backendSession.roomResultController.addRoomResult(roomResult.getRoomId(), roomResult.getUserId(), roomResult.getUserName(), 0.0);
        }
        checkRoom(1);
    }

}
