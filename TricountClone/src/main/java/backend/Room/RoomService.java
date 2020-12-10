package backend.Room;

import backend.BackendException;
import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class RoomService {
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_ROOM;

    private static final String ROOM_FORMAT = "RoomId: %-20s Name: %-10s \n";


    public RoomService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();
    }

    private void prepareStatements() throws BackendException {
        try {
            SELECT_ALL_FROM_ROOM = session.prepare("SELECT * FROM room;");

        } catch (Exception e) {
            throw new BackendException("Could not prepare statements. " + e.getMessage() + ".", e);
        }
        logger.info("Statements prepared for RoomService");
    }

    public String selectAllRooms() throws BackendException {
        StringBuilder builder = new StringBuilder();
        BoundStatement bs = new BoundStatement(SELECT_ALL_FROM_ROOM);

        ResultSet rs = null;

        try{
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. " +e.getMessage() +".",e);
        }

        for(Row row:rs){
            String roomId = row.getUUID("roomId").toString();
            String name = row.getString("name");

            builder.append(String.format(ROOM_FORMAT,roomId,name));
        }
        return  builder.toString();
    }
}
