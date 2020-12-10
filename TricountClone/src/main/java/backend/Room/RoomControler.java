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


}
