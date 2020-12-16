package backend.Payment;

import backend.BackendException;
import backend.User.UserService;
import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_PAYMENTS;
    private static PreparedStatement SELECT_PAYMENTS_BY_ROOM;
    private static PreparedStatement SELECT_PAYMENTS_BY_ROOM_ANS_USER;
    private static PreparedStatement INSERT_PAYMENT;
    private static PreparedStatement DELETE_PAYMENT_BY_ROOM;
    private static PreparedStatement DELETE_PAYMENT_BY_USER;
    private static PreparedStatement DELETE_PAYMENT_BY_ID;

    private static final String PAYMENT_FORMAT = "PaymentId: %-20s \n" +
            "RoomId: %-20s \n" +
            "Amount: %-20s \n" +
            "Payer: %-20s \n" +
            "Receiver: %-20s";



    public PaymentService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();

    }

    private void prepareStatements() throws BackendException {
        try{
            SELECT_ALL_FROM_PAYMENTS = session.prepare("SELECT * FROM payments");
            SELECT_PAYMENTS_BY_ROOM = session.prepare("SELECT * FROM payments WHERE room = ?");
            SELECT_PAYMENTS_BY_ROOM_ANS_USER = session.prepare("SELECT * FROM payments WHERE room =? AND payer = ?");
            INSERT_PAYMENT = session.prepare("INSERT INTO payments (paymentId,room,amount,payer,receiver) VALUES (?,?,?,?,?)");
            DELETE_PAYMENT_BY_ROOM = session.prepare("DELETE FROM payments WHERE room = ?");
            DELETE_PAYMENT_BY_USER = session.prepare("DELETE FROM payments WHERE room = ? AND payer = ?");
            DELETE_PAYMENT_BY_USER = session.prepare("DELETE FROM payments WHERE room = ? AND payer = ? AND paymentid = ?");

        } catch (Exception e){
            throw new BackendException("Could not prepare statements. "+e.getMessage()+".",e);
        }
        logger.info("Statements prepared for PaymentService");
    }

    public String selectAllPayments() throws BackendException {
        System.out.println("Get here-1");
        StringBuilder builder = new StringBuilder();
        BoundStatement bs = new BoundStatement(SELECT_ALL_FROM_PAYMENTS);

        ResultSet rs = null;
        System.out.println("Get here2");
        try{
            rs = session.execute(bs);
        } catch (Exception e){
            throw new BackendException("Could not perform a query. "+ e.getMessage()+ ".",e);
        }
        System.out.println("Get here1");

        for(Row row : rs){
            String paymentId = row.getUUID("paymentId").toString();
            String roomId = row.getUUID("room").toString();
            Double amount = row.getDouble("amount");
            String payer = row.getUUID("payer").toString();
            String receiver = row.getUUID("receiver").toString();
            builder.append(String.format(PAYMENT_FORMAT,paymentId,roomId,amount.toString(),payer,receiver));
        }
        System.out.println("Get here3");
        return builder.toString();
    }

}
