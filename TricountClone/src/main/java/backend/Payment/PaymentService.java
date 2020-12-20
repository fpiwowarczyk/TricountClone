package backend.Payment;

import backend.BackendException;
import backend.User.UserService;
import com.datastax.driver.core.*;
import jnr.ffi.Struct;
import org.apache.cassandra.cql3.statements.Bound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_PAYMENTS;
    private static PreparedStatement SELECT_PAYMENTS_BY_ROOM;
    private static PreparedStatement SELECT_PAYMENTS_BY_ROOM_AND_USER;
    private static PreparedStatement SELECT_PAYMENTS_BY_ROOM_AND_USER_AND_ID;
    private static PreparedStatement INSERT_PAYMENT;
    private static PreparedStatement DELETE_PAYMENT_BY_ROOM;
    private static PreparedStatement DELETE_PAYMENT_BY_USER;
    private static PreparedStatement DELETE_PAYMENT_BY_ID;

    private static final String PAYMENT_FORMAT = "PaymentId: %-20s \n" +
            "RoomId: %-20s \n" +
            "Amount: %-20f \n" +
            "Payer: %-20s \n" +
            "Receiver: %-20s \n" +
            "----------------\n";



    public PaymentService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();

    }

    private void prepareStatements() throws BackendException {
        try{
            SELECT_ALL_FROM_PAYMENTS = session.prepare("SELECT * FROM payments");
            SELECT_PAYMENTS_BY_ROOM = session.prepare("SELECT * FROM payments WHERE room = ?");
            SELECT_PAYMENTS_BY_ROOM_AND_USER = session.prepare("SELECT * FROM payments WHERE room =? AND payer = ?");
            SELECT_PAYMENTS_BY_ROOM_AND_USER_AND_ID = session.prepare("SELECT * FROM payments WHERE room =? AND payer = ? AND paymentId=?");
            INSERT_PAYMENT = session.prepare("INSERT INTO payments (paymentId,room,amount,payer,receiver) VALUES (?,?,?,?,?)");
            DELETE_PAYMENT_BY_ROOM = session.prepare("DELETE FROM payments WHERE room = ?");
            DELETE_PAYMENT_BY_USER = session.prepare("DELETE FROM payments WHERE room = ? AND payer = ?");
            DELETE_PAYMENT_BY_ID = session.prepare("DELETE FROM payments WHERE room = ? AND payer = ? AND paymentid = ?");

        } catch (Exception e){
            throw new BackendException("Could not prepare statements. "+e.getMessage()+".",e);
        }
        logger.info("Statements prepared for PaymentService");
    }

    public String selectAllPayments() throws BackendException {
        StringBuilder builder = new StringBuilder();
        BoundStatement bs = new BoundStatement(SELECT_ALL_FROM_PAYMENTS);

        ResultSet rs = null;

        try{
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. "+ e.getMessage()+ ".",e);
        }

        for(Row row : rs){
            String paymentId = row.getUUID("paymentId").toString();
            String roomId = row.getUUID("room").toString();
            double amount = row.getDouble("amount");
            String payer = row.getUUID("payer").toString();
            String receiver = row.getUUID("receiver").toString();
            builder.append(String.format(PAYMENT_FORMAT,paymentId,roomId,amount,payer,receiver));
        }
        return builder.toString();
    }

    public String selectPaymentsByRoom(String room) throws BackendException {
        StringBuilder builder = new StringBuilder();
        BoundStatement bs = new BoundStatement(SELECT_PAYMENTS_BY_ROOM);

        ResultSet rs = null;

        try{
            bs.bind(UUID.fromString(room));
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. " +e.getMessage()+".",e);
        }

        for(Row row: rs){
            String paymentId = row.getUUID("paymentId").toString();
            String roomId = row.getUUID("room").toString();
            double amount = row.getDouble("amount");
            String payer = row.getUUID("payer").toString();
            String receiver = row.getUUID("receiver").toString();
            builder.append(String.format(PAYMENT_FORMAT,paymentId,roomId,amount,payer,receiver));
        }

        return builder.toString();
    }

    public String selectPaymentsByRoomAndUser(String room, String user) throws BackendException{
        StringBuilder builder = new StringBuilder();
        BoundStatement bs = new BoundStatement(SELECT_PAYMENTS_BY_ROOM_AND_USER);

        ResultSet rs = null;
        try {
            bs.bind(UUID.fromString(room),UUID.fromString(user));
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. " + e.getMessage()+".",e);
        }

        for(Row row: rs){
            String paymentId = row.getUUID("paymentId").toString();
            String roomId = row.getUUID("room").toString();
            double amount = row.getDouble("amount");
            String payer = row.getUUID("payer").toString();
            String receiver = row.getUUID("receiver").toString();
            builder.append(String.format(PAYMENT_FORMAT,paymentId,roomId,amount,payer,receiver));
        }
        return builder.toString();
    }

    public String selectPaymentByRoomAndUserAndPaymentId(String room, String user, String payment) throws BackendException {
        StringBuilder builder = new StringBuilder();
        BoundStatement bs = new BoundStatement(SELECT_PAYMENTS_BY_ROOM_AND_USER_AND_ID);

        ResultSet rs = null;
        try {
            bs.bind(UUID.fromString(room),UUID.fromString(user),UUID.fromString(payment));
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. " + e.getMessage()+".",e);
        }

        for(Row row: rs){
            String paymentId = row.getUUID("paymentId").toString();
            String roomId = row.getUUID("room").toString();
            double amount = row.getDouble("amount");
            String payer = row.getUUID("payer").toString();
            String receiver = row.getUUID("receiver").toString();
            builder.append(String.format(PAYMENT_FORMAT,paymentId,roomId,amount,payer,receiver));
        }
        return builder.toString();
    }

    public void insertPayment(String room, double amount, String payer, String receiver) throws BackendException {
        BoundStatement bs = new BoundStatement(INSERT_PAYMENT);

        UUID paymentId = UUID.randomUUID();
        try {
            bs.bind(paymentId,UUID.fromString(room),amount,UUID.fromString(payer),UUID.fromString(receiver));
            session.execute(bs);
        }catch(Exception e) {
            throw new BackendException("Could not perform a query. " + e.getMessage() + ".",e);
        }
    }

    public void deletePaymentsByRoom(String room){
        BoundStatement bs = new BoundStatement(DELETE_PAYMENT_BY_ROOM);
    }

    public void deletePaymentsByUser(String room, String user){
        BoundStatement bs = new BoundStatement(DELETE_PAYMENT_BY_USER);
    }

    public void deletePaymentById(){

    }

}
