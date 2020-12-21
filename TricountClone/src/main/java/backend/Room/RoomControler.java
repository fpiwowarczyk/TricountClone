package backend.Room;

import backend.BackendException;
import com.datastax.driver.core.Session;

import java.util.LinkedList;

public class RoomControler {

    RoomService roomService;

    public RoomControler(Session session) throws BackendException {
        this.roomService = new RoomService(session);
    }

    public LinkedList<RoomDTO> getRooms() throws BackendException {
        return this.roomService.selectAllRooms();
    }

    public RoomDTO getRoom(String name) throws BackendException {
        return this.roomService.selectRoomByName(name);
    }

    public void addRoom(String name,String roomId) throws BackendException {
        roomService.insertRoom(name,roomId);
    }

    public void deleteRoom(String name,String roomId) throws BackendException {
        roomService.deleteRoomById(name,roomId);
    }


}
