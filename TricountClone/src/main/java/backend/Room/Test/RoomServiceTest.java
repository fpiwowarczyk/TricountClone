package backend.Room.Test;

import backend.BackendException;
import backend.Room.RoomDTO;
import backend.Room.RoomService;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {
    RoomService roomService;

    public RoomServiceTest() throws BackendException {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        try {
            Session session = cluster.connect("tricount");
            this.roomService = new RoomService(session);
        } catch (Exception e) {
            throw new BackendException("Could not connect to the cluster. " + e.getMessage() + ".", e);
        }
        cleanUp();

    }

    @Test
    void shouldInsertReadAndRemoveAllRooms() throws BackendException {
        // given
        RoomDTO room1 = new RoomDTO("TestingRoom1");
        RoomDTO room2 = new RoomDTO("TestRoom2");

        // when
        roomService.insertRoom(room1.getName(),room1.getRoomId());
        roomService.insertRoom(room2.getName(),room2.getRoomId());

        LinkedList<RoomDTO> rooms = roomService.selectAllRooms();

        // then
        Assertions.assertTrue(room1.equals(rooms.get(1)));
        Assertions.assertTrue(room2.equals(rooms.get(0)));
        cleanUp();
    }


    @Test
    void shouldSelectRoomByName() throws BackendException {
        RoomDTO room1 = new RoomDTO("TestingRoom1");
        RoomDTO room2 = new RoomDTO("TestRoom2");

        roomService.insertRoom(room1.getName(),room1.getRoomId());
        roomService.insertRoom(room2.getName(),room2.getRoomId());

        RoomDTO room= roomService.selectRoomById(room1.getRoomId());

        Assertions.assertTrue(room.equals(room1));
        Assertions.assertFalse(room.equals(room2));
    }

    @After
    public void cleanUp() throws BackendException {
        LinkedList<RoomDTO> result = this.roomService.selectAllRooms();
        while (!result.isEmpty()){
            this.roomService.deleteRoomById(result.get(0).getName(),result.get(0).getRoomId());
            result = this.roomService.selectAllRooms();
        }
        result = this.roomService.selectAllRooms();
        assert(result.isEmpty());
    }



}
