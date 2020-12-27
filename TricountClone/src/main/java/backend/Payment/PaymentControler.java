package backend.Payment;

import backend.BackendException;
import com.datastax.driver.core.Session;

import java.util.LinkedList;

public class PaymentControler {

    PaymentService paymentService;

    public PaymentControler(Session session) throws BackendException {
        this.paymentService = new PaymentService(session);
    }

    public LinkedList<PaymentDTO> getAllPayments() throws BackendException {
        return this.paymentService.selectAllPayments();
    }

    public LinkedList<PaymentDTO> getPaymentsByRoom(String room) throws BackendException {
        return this.paymentService.selectPaymentsByRoom(room);
    }

    public LinkedList<PaymentDTO> getPaymentByRoomAndPayer(String room, String payer) throws BackendException {
        return this.paymentService.selectPaymentsByRoomAndUser(room,payer);
    }

    public PaymentDTO getSinglePayment(String room, String payer, String payment) throws BackendException {
        return this.paymentService.selectPaymentByRoomAndUserAndPaymentId(room,payer,payment);
    }

    public void addPayment(String paymentId,String room, double amount, String payer, String receiver) throws BackendException {
        this.paymentService.insertPayment(paymentId,room, amount, payer, receiver);
    }

    public void deletePaymentsByRoom(String room) throws BackendException {
        this.paymentService.deletePaymentsByRoom(room);
    }

    public void deletePaymentsByRoomAndPayer(String room, String user) throws BackendException {
        this.paymentService.deletePaymentsByUser(room,user);
    }
//
//    public void deleteSinglePayment(){
//
//    }



}
