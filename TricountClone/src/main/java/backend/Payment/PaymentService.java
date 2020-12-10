package backend.Payment;

import backend.BackendException;
import backend.User.UserService;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final Session session;

    private static PreparedStatement SELECT_ALL_FROM_PAYMENTS;


    public PaymentService(Session session) throws BackendException {
        this.session = session;

        prepareStatements();

    }

    private void prepareStatements() throws BackendException {
        try{
            SELECT_ALL_FROM_PAYMENTS = session.prepare("SELECT * FROM payments;");

        } catch (Exception e){
            throw new BackendException("Could not prepare statements. "+e.getMessage()+".",e);
        }
        logger.info("Statements prepared for PaymentService");
    }
}
