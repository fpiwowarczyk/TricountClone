package backend.Room;

import backend.BackendException;
import com.datastax.driver.core.*;

import java.util.LinkedList;
import java.util.UUID;

public class RoomService {

    private final Session session;

    private static PreparedStatement SELECT_ALL_ROOMS;
    private static PreparedStatement SELECT_ROOM_BY_ID;
    private static PreparedStatement INSERT_ROOM;
    private static PreparedStatement DELETE_ROOM_BY_ID;

    public RoomService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();
    }

    private void prepareStatements() throws BackendException {
        try {
            SELECT_ALL_ROOMS = session.prepare("SELECT * FROM room;");
            SELECT_ROOM_BY_ID = session.prepare("SELECT * FROM room WHERE roomId = ?");
            INSERT_ROOM = session.prepare("INSERT INTO room (name,roomId) VALUES (?,?)");
            DELETE_ROOM_BY_ID = session.prepare("DELETE FROM room WHERE name = ? AND roomId = ?");
        } catch (Exception e) {
            throw new BackendException("Could not prepare statements. " + e.getMessage() + ".", e);
        }
    }

    public LinkedList<RoomDTO> selectAllRooms() throws BackendException {
        BoundStatement bs = new BoundStatement(SELECT_ALL_ROOMS);
        LinkedList<RoomDTO> rooms = new LinkedList<>();
        ResultSet rs = null;

        try {
            rs = session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }

        for (Row row : rs) {
            String roomId = row.getUUID("roomId").toString();
            String name = row.getString("name");

            rooms.add(new RoomDTO(name, roomId));
        }
        return rooms;
    }

    public RoomDTO selectRoomById(String roomId) throws BackendException {
        BoundStatement bs = new BoundStatement(SELECT_ROOM_BY_ID);

        ResultSet rs = null;

        try {
            bs.bind(UUID.fromString(roomId));
            rs = session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform q query. " + e.getMessage() + ".", e);
        }
        if(rs.iterator().hasNext()){
            Row row = rs.one();
            String roomName = row.getString("name");
            String id = row.getUUID("roomId").toString();
            return new RoomDTO(roomName, id);
        } else {
            throw new BackendException("Didn't find any matching names");
        }



    }

    public void insertRoom(String name,String roomId) throws BackendException {
        BoundStatement bs = new BoundStatement(INSERT_ROOM);
        try {
            bs.bind(name,UUID.fromString(roomId));
            session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }
    }

    public void deleteRoomById(String name, String roomId) throws BackendException {
        BoundStatement bs = new BoundStatement(DELETE_ROOM_BY_ID);

        try {
            bs.bind(name, UUID.fromString(roomId));
            session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }

    }
}
