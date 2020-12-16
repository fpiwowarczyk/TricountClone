package backend.Room;

import backend.BackendException;
import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.UUID;

public class RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_ROOM;
    private static PreparedStatement SELECT_ROOM_BY_NAME;
    private static PreparedStatement INSERT_ROOM;
    private static PreparedStatement DELETE_ROOM_BY_ID;

    private static final String ROOM_FORMAT = "RoomId: %-20s Name: %-10s \n";


    public RoomService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();
    }

    private void prepareStatements() throws BackendException {
        try {
            SELECT_ALL_FROM_ROOM = session.prepare("SELECT * FROM room;");
            SELECT_ROOM_BY_NAME = session.prepare("SELECT * FROM room WHERE name = ?");
            INSERT_ROOM = session.prepare("INSERT INTO room (roomId,name) VALUES (?,?)");
            DELETE_ROOM_BY_ID = session.prepare("DELETE FROM room WHERE name = ? AND roomId = ?");
        } catch (Exception e) {
            throw new BackendException("Could not prepare statements. " + e.getMessage() + ".", e);
        }
        logger.info("Statements prepared for RoomService");
    }

    public String selectAllRooms() throws BackendException {
        StringBuilder builder = new StringBuilder();
        BoundStatement bs = new BoundStatement(SELECT_ALL_FROM_ROOM);

        ResultSet rs = null;

        try {
            rs = session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }

        for (Row row : rs) {
            String roomId = row.getUUID("roomId").toString();
            String name = row.getString("name");

            builder.append(String.format(ROOM_FORMAT, roomId, name));
        }
        return builder.toString();
    }

    public RoomDTO selectRoomByName(String name) throws BackendException {
        BoundStatement bs = new BoundStatement(SELECT_ROOM_BY_NAME);

        ResultSet rs = null;

        try {
            bs.bind(name);
            rs = session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform q query. " + e.getMessage() + ".", e);
        }
        String Id = "Not Initialized";
        String roomName = "Not Initialized";
        for (Row row : rs) {
            Id = row.getUUID("roomId").toString();
            roomName= row.getString("name");
        }
        return new RoomDTO(Id, roomName);
    }

    public void insertRoom(String name) throws BackendException {
        BoundStatement bs = new BoundStatement(INSERT_ROOM);


        UUID roomID = UUID.randomUUID();

        try {
            bs.bind(roomID, name);
            session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }
    }

    public void deleteRoomById(String name,String roomId) throws BackendException {
        BoundStatement bs = new BoundStatement(DELETE_ROOM_BY_ID);

        try {
            bs.bind(name,UUID.fromString(roomId));
            session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".", e);
        }

    }
}
