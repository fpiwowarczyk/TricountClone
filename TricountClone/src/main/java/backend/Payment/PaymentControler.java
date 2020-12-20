package backend.Payment;

import backend.BackendException;
import com.datastax.driver.core.Session;

public class PaymentControler {

    PaymentService paymentService;

    public PaymentControler(Session session) throws BackendException {
        this.paymentService = new PaymentService(session);
    }

    public String getAllPayments() throws BackendException {
        return this.paymentService.selectAllPayments();
    }

    public String getPaymentsByRoom(String room) throws BackendException {
        return this.paymentService.selectPaymentsByRoom(room);
    }

    public String getPaymentByRoomAndPayer(String room,String payer) throws BackendException {
        return this.paymentService.selectPaymentsByRoomAndUser(room,payer);
    }

    public String getSinglePayment(String room, String payer, String payment) throws BackendException {
        return this.paymentService.selectPaymentByRoomAndUserAndPaymentId(room,payer,payment);
    }

    public void addPayment(String room, double amount, String payer, String receiver) throws BackendException {
        this.paymentService.insertPayment(room, amount, payer, receiver);
    }

    public void deletePaymentsByRoom(String room){
        this.paymentService.deletePaymentsByRoom(room);
    }

    public void deletePaymentsByRoomAndPayer(String room, String user){
        this.paymentService.deletePaymentsByUser(room,user);
    }
//
//    public void deleteSinglePayment(){
//
//    }



}
