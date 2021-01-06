package backend.RoomResult;

import backend.BackendException;
import com.datastax.driver.core.Session;

import java.util.LinkedList;

public class RoomResultController {

    RoomResultService roomResultService;

    public RoomResultController(Session session) throws BackendException {
        this.roomResultService = new RoomResultService(session);
    }

    public LinkedList<RoomResultDTO> getAllResults() throws BackendException {
        return this.roomResultService.selectAllRoomResults();
    }

    public LinkedList<RoomResultDTO> getRoomResults(String room) throws BackendException {
        return this.roomResultService.selectResultForRoom(room);
    }

    public void addRoomResult(String room, String user, String userName, Double money) throws BackendException {
        roomResultService.insertNewResultForRoom(room, user, userName, money);
    }

    public void deleteRoomResult(String room) throws BackendException {
        roomResultService.deleteResultForRoom(room);
    }
}
