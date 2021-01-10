package backend;

import backend.Payment.PaymentControler;
import backend.Room.RoomControler;
import backend.RoomResult.RoomResultController;
import backend.User.UserControler;
import com.datastax.driver.core.*;


public class BackendSession {


    private final Session session;


    public PaymentControler paymentControler;
    public RoomControler roomControler;
    public RoomResultController roomResultController;
    public UserControler userControler;

    public BackendSession(String contactPoint, String keyspace) throws BackendException {
        Cluster cluster = Cluster.builder().addContactPoint(contactPoint).withQueryOptions(new QueryOptions().setConsistencyLevel(ConsistencyLevel.ONE)).build();
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
        this.roomResultController = new RoomResultController(session);
        this.userControler = new UserControler(session);
    }

    public void endSession() throws BackendException {
        try {
            if (session != null) {
                session.getCluster().close();
            }
        } catch (Exception e) {
            throw new BackendException("Could not connect to the cluster. " + e.getMessage() + ".", e);
        }
    }
}