package backend.RoomResult;

import backend.BackendException;
import com.datastax.driver.core.*;
import org.apache.cassandra.cql3.statements.Bound;
import org.junit.jupiter.api.parallel.Execution;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class RoomResultService {

    private final Session session;

    private static PreparedStatement SELECT_RESULTS_FOR_ROOM;
    private static PreparedStatement SELECT_ALL_RESULTS;
    private static PreparedStatement DELETE_RESULTS_FOR_ROOM;
    private static PreparedStatement DELETE_USER_RESULTS_FOR_ROOM;
    private static PreparedStatement INSERT_NEW_RESULT_FOR_ROOM;
    private static PreparedStatement SELECT_USER_RESULTS_FOR_ROOM;

    public RoomResultService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();
    }

    private void prepareStatements() throws BackendException {
        try{
            SELECT_RESULTS_FOR_ROOM = session.prepare("SELECT * FROM roomresult WHERE room = ?");
            SELECT_USER_RESULTS_FOR_ROOM = session.prepare("SELECT * FROM roomresult WHERE room = ? and user = ?");
            SELECT_ALL_RESULTS = session.prepare("SELECT * FROM roomresult");
            DELETE_RESULTS_FOR_ROOM = session.prepare("DELETE FROM roomresult WHERE room = ?");
            DELETE_USER_RESULTS_FOR_ROOM = session.prepare("DELETE FROM roomresult WHERE room = ? and user = ?");
            INSERT_NEW_RESULT_FOR_ROOM = session.prepare("INSERT INTO roomresult (room,user,userName,money) VALUES (?,?,?,?)");

        }catch (Exception e){
            throw new BackendException("Could not prepare statements. " + e.getMessage()+".",e);
        }
    }

    public LinkedList<RoomResultDTO> selectAllRoomResults() throws BackendException {
        BoundStatement bs= new BoundStatement(SELECT_ALL_RESULTS);
        LinkedList<RoomResultDTO> result = new LinkedList<>();
        ResultSet rs = null;
        try {
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. "+ e.getMessage()+".",e);
        }

        for(Row row : rs){
            String roomId = row.getUUID("room").toString();
            String user = row.getUUID("user").toString();
            String userName = row.getString("userName");
            Double money = row.getDouble("money");
            result.add(new RoomResultDTO(roomId,user,userName,money));
        }
        return result;
    }

    public LinkedList<RoomResultDTO> selectResultForRoom(String room) throws BackendException {
        BoundStatement bs = new BoundStatement(SELECT_RESULTS_FOR_ROOM);
        LinkedList<RoomResultDTO> result = new LinkedList<>();
        ResultSet rs = null;

        try {
            bs.bind(UUID.fromString(room));
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. "+ e.getMessage()+".",e);
        }

        for(Row row : rs){
            String roomId = row.getUUID("room").toString();
            String user = row.getUUID("user").toString();
            String userName = row.getString("userName");
            Double money = row.getDouble("money");
            result.add(new RoomResultDTO(roomId,user,userName,money));
        }
        return result;
    }

    public RoomResultDTO selectUserResultForRoom(String room,String userId) throws BackendException {
        BoundStatement bs = new BoundStatement(SELECT_USER_RESULTS_FOR_ROOM);
        ResultSet rs = null;

        try {
            bs.bind(UUID.fromString(room),UUID.fromString(userId));
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. "+ e.getMessage()+".",e);
        }

        for(Row row : rs){
            String roomId = row.getUUID("room").toString();
            String user = row.getUUID("user").toString();
            String userName = row.getString("userName");
            Double money = row.getDouble("money");
            RoomResultDTO result = new RoomResultDTO(roomId,user,userName,money);
            return result;
        }
        return  null;
    }

    public void insertNewResultForRoom(String room,String user,String userName,Double money) throws BackendException {
        BoundStatement bs = new BoundStatement(INSERT_NEW_RESULT_FOR_ROOM);

        try {
            bs.bind(UUID.fromString(room),UUID.fromString(user),userName,money);
            session.execute(bs);
        } catch (Exception e) {
            throw new BackendException("Could not perform a query. "+e.getMessage() +".",e);
        }
    }

    public void deleteResultForRoom(String room) throws BackendException {
        BoundStatement bs = new BoundStatement(DELETE_RESULTS_FOR_ROOM);

        try{
            bs.bind(UUID.fromString(room));
            session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. "+ e.getMessage()+".",e);
        }
    }

    public void deleteUserResultForRoom(String room,String user) throws BackendException {
        BoundStatement bs = new BoundStatement(DELETE_USER_RESULTS_FOR_ROOM);

        try{
            bs.bind(UUID.fromString(room),UUID.fromString(user));
            session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. "+ e.getMessage()+".",e);
        }
    }

}
