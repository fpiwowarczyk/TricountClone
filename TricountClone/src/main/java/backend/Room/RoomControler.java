package backend.Room;

import backend.BackendException;
import com.datastax.driver.core.Session;

public class RoomControler {

    RoomService roomService;

    public RoomControler(Session session) throws BackendException {
        this.roomService = new RoomService(session);
    }

    public String getRooms() throws BackendException {
        return this.roomService.selectAllRooms();
    }

    public RoomDTO getRoom(String name) throws BackendException {
        return this.roomService.selectRoomByName(name);
    }

    public void addRoom(String name) throws BackendException {
        roomService.insertRoom(name);
    }

    public void deleteRoom(String name,String roomId) throws BackendException {
        roomService.deleteRoomById(name,roomId);
    }


}
