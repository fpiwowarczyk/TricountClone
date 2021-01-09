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

public class StressTests {
    private final BackendSession backendSession;
    public StressTests(BackendSession backendSession) throws BackendException {
        this.backendSession = backendSession;
    }

    class payTest extends Thread{
        private BackendSession backendSession;
        private LinkedList<UserDTO> userList;
        private LinkedList<RoomResultDTO> roomResultDTOS;
        private int user;
        Random r = new Random();
        public payTest(BackendSession backendSession,int user,LinkedList<UserDTO> userList){
            this.backendSession = backendSession;
            this.userList = userList;
            this.user = user;
        }
        public void run() {
            for (int i = 0; i < 10; i++) {
                double myMoney = 0.0;
                double receiverMoney = 0.0;
                double money = Math.round((r.nextDouble() * 200.0 - 100.0)*100.0)/100.0;
                int receiver = 0;
                boolean finish = true;
                while (finish) {
                    receiver = r.nextInt(2);
                    if (receiver != user) {
                        finish = false;
                    }
                }
                try {
                    roomResultDTOS = backendSession.roomResultController.getRoomResults(userList.get(0).getRoomId());
                    for (RoomResultDTO roomResult : roomResultDTOS) {
                        if (roomResult.getUserId().equals(userList.get(user).getUserId())) {
                            //myMoney = backendSession.roomResultController.getUserRoomResults(userList.get(user).getRoomId(),userList.get(user).getUserId()).getMoney()+money;
                            backendSession.roomResultController.addRoomResult(roomResult.getRoomId(), roomResult.getUserId(), roomResult.getUserName(), (backendSession.roomResultController.getUserRoomResults(roomResult.getRoomId(),roomResult.getUserId()).getMoney()+money));

                        }
                        if (roomResult.getUserId().equals(userList.get(receiver).getUserId())) {
                            //receiverMoney = backendSession.roomResultController.getUserRoomResults(userList.get(user).getRoomId(),userList.get(receiver).getUserId()).getMoney()-money;
                            backendSession.roomResultController.addRoomResult(roomResult.getRoomId(), roomResult.getUserId(), roomResult.getUserName(), (backendSession.roomResultController.getUserRoomResults(roomResult.getRoomId(),roomResult.getUserId()).getMoney()-money));
                        }
                    }

                } catch (BackendException e) {
                }
            }
        }
    }

    public void startTest() throws BackendException , InterruptedException{
        LinkedList<UserDTO> userList = backendSession.userControler.getUsers();
        /*ExecutorService threadPool = Executors.newFixedThreadPool(5); <- to daje takie same wyniki
        for (int i=0;i<5;i++){
            threadPool.execute(new payTest(backendSession,i,userList));
        }
        threadPool.shutdown();
*/
        payTest p1 = new payTest(backendSession,0,userList);
        payTest p2 = new payTest(backendSession,1,userList);
        //payTest p3 = new payTest(backendSession,2,userList);
        //payTest p4 = new payTest(backendSession,3,userList);
        //payTest p5 = new payTest(backendSession,4,userList);
        p1.start();
        p2.start();
        //p3.start();
        //p4.start();
        //p5.start();
        p1.join();
        p2.join();
        //p3.join();
        //p4.join();
        //p5.join();
    }

    public void checkSum() throws BackendException{
        double sum = 0.0;
        LinkedList<UserDTO> userList = backendSession.userControler.getUsers();
        LinkedList<RoomResultDTO> roomResultDTOS = backendSession.roomResultController.getRoomResults(userList.get(0).getRoomId());
        for (RoomResultDTO roomResult : roomResultDTOS) {
            sum += roomResult.getMoney();
        }
        System.out.println("Suma "+sum);
    }

}
