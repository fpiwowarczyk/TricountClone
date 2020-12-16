package backend.Payment;

import backend.BackendException;
import com.datastax.driver.core.Session;

public class PaymentControler {

    PaymentService paymentService;

    public PaymentControler(Session session) throws BackendException {
        this.paymentService = new PaymentService(session);
    }

    public String getPayments() throws BackendException {
        System.out.println("Get here5");
        return this.paymentService.selectAllPayments();
    }
}
