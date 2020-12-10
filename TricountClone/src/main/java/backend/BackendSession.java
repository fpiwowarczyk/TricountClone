package backend;

import backend.Payment.PaymentControler;
import backend.Room.RoomControler;
import backend.RoomResult.RoomResultControler;
import backend.User.UserControler;
import backend.User.UserService;
import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BackendSession {

    private static final Logger logger = LoggerFactory.getLogger(BackendSession.class);

    private final Session session;


    public PaymentControler paymentControler;
    public RoomControler roomControler;
    public RoomResultControler roomResultControler;
    public UserControler userControler;

    public BackendSession(String contactPoint, String keyspace) throws BackendException {
        Cluster cluster = Cluster.builder().addContactPoint(contactPoint).build();
        try {
            session = cluster.connect(keyspace);
        } catch (Exception e) {
            throw new BackendException("Could not connect to the cluster. " + e.getMessage() + ".", e);
        }
        initializeData();
    }

    private void initializeData() throws BackendException {
        this.paymentControler = new PaymentControler(session);
        this.roomControler = new RoomControler(session);
        this.roomResultControler = new RoomResultControler(session);
        this.userControler = new UserControler(session);
    }


    public void endSession() {
        try {
            if (session != null) {
                session.getCluster().close();
            }
        } catch (Exception e) {
            logger.error("Could not close existing cluster", e);
        }
    }


}