package backend.RoomResult.Tests;

import backend.BackendException;
import backend.RoomResult.RoomResultDTO;
import backend.RoomResult.RoomResultService;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;


class RoomResultServiceTest {

    RoomResultService roomResultService;

    public RoomResultServiceTest() throws BackendException {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        try {
            Session session = cluster.connect("tricount");
            this.roomResultService = new RoomResultService(session);
        } catch (Exception e) {
            throw new BackendException("Could not connect to the cluster. " + e.getMessage() + ".", e);
        }
    }

    @Test
    void shouldAddRoomResultAndRemoveIt() throws BackendException {
        //given
        RoomResultDTO roomResult1 = new RoomResultDTO("ab036a88-3b36-11eb-adc1-0242ac120002", "ab036a88-3b36-11eb-adc1-0242ac120020", 10.0);
        RoomResultDTO roomResult2 = new RoomResultDTO("ab036a88-3b36-11eb-adc1-0242ac120002", "ab036a88-3b36-11eb-adc1-0242ac120021", -10.0);
        RoomResultDTO roomResult3 = new RoomResultDTO("ab036a88-3b36-11eb-adc1-0242ac120002", "ab036a88-3b36-11eb-adc1-0242ac120022", -12.0);
        RoomResultDTO roomResult4 = new RoomResultDTO("ab036a88-3b36-11eb-adc1-0242ac120002", "ab036a88-3b36-11eb-adc1-0242ac120023", 12.0);
        RoomResultDTO roomResult5 = new RoomResultDTO("ab036a88-3b36-11eb-adc1-0242ac120003", "ab036a88-3b36-11eb-adc1-0242ac120024", 12.0);

        //when
        this.roomResultService.insertNewResultForRoom(
                roomResult1.getRoomId(),
                roomResult1.getUserId(),
                roomResult1.getMoney());

        this.roomResultService.insertNewResultForRoom(
                roomResult2.getRoomId(),
                roomResult2.getUserId(),
                roomResult2.getMoney());
        this.roomResultService.insertNewResultForRoom(
                roomResult3.getRoomId(),
                roomResult3.getUserId(),
                roomResult3.getMoney());
        this.roomResultService.insertNewResultForRoom(
                roomResult4.getRoomId(),
                roomResult4.getUserId(),
                roomResult4.getMoney());
        this.roomResultService.insertNewResultForRoom(
                roomResult5.getRoomId(),
                roomResult5.getUserId(),
                roomResult5.getMoney());

        LinkedList<RoomResultDTO> roomResults = this.roomResultService.selectResultForRoom("ab036a88-3b36-11eb-adc1-0242ac120002");
        LinkedList<RoomResultDTO> allRoomResults = this.roomResultService.selectAllRoomResults();

        //then
        Assertions.assertEquals(4, roomResults.size());
        Assertions.assertEquals(5, allRoomResults.size());
        cleanUp();
    }

    @After
    public void cleanUp() throws BackendException {
        LinkedList<RoomResultDTO> results = this.roomResultService.selectAllRoomResults();
        while (!results.isEmpty()) {
            this.roomResultService.deleteResultForRoom(results.get(0).getRoomId());
            results = this.roomResultService.selectAllRoomResults();
        }
        results = this.roomResultService.selectAllRoomResults();
        assert (results.isEmpty());
    }

}