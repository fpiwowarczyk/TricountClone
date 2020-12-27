package backend.Payment.Tests;

import backend.BackendException;
import backend.Payment.PaymentDTO;
import backend.Payment.PaymentService;
import backend.Room.RoomDTO;
import backend.User.UserService;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class PaymentServiceTest {
    private final PaymentService paymentService;


    public PaymentServiceTest() throws BackendException {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        try {
            Session session = cluster.connect("tricount");
            this.paymentService = new PaymentService(session);
        } catch (Exception e) {
            throw new BackendException("Could not connect to the cluster. " + e.getMessage() + ".", e);
        }
        cleanUp();
    }

    @Test
    void shouldInsertAndSelectAllUsers() throws BackendException {
        //given
        PaymentDTO payment1 = new PaymentDTO(
                "5fa6666a-37bd-11eb-adc1-0242ac120012",
                10.0,
                "ab036100-3b36-11eb-adc1-0242ac120004",
                "ab036100-3b36-11eb-adc1-0242ac120005");

        PaymentDTO payment2 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005"
        );

        //when
        this.paymentService.insertPayment(
                payment1.getPaymentId(),
                payment1.getRoomId(),
                payment1.getAmount(),
                payment1.getPayer(),
                payment1.getReceiver());
        this.paymentService.insertPayment(
                payment2.getPaymentId(),
                payment2.getRoomId(),
                payment2.getAmount(),
                payment2.getPayer(),
                payment2.getReceiver());

        LinkedList<PaymentDTO> payments = this.paymentService.selectAllPayments();

        Assertions.assertTrue(payments.get(0).equals(payment2));
        Assertions.assertTrue(payments.get(1).equals(payment1));
        cleanUp();
    }

    @Test
    void shouldSelectPaymentByRoom() throws BackendException {
        //given
        PaymentDTO payment1 = new PaymentDTO(
                "5fa6666a-37bd-11eb-adc1-0242ac120012",
                10.0,
                "ab036100-3b36-11eb-adc1-0242ac120004",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment2 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment3 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        this.paymentService.insertPayment(
                payment1.getPaymentId(),
                payment1.getRoomId(),
                payment1.getAmount(),
                payment1.getPayer(),
                payment1.getReceiver());
        this.paymentService.insertPayment(
                payment2.getPaymentId(),
                payment2.getRoomId(),
                payment2.getAmount(),
                payment2.getPayer(),
                payment2.getReceiver());
        this.paymentService.insertPayment(
                payment3.getPaymentId(),
                payment3.getRoomId(),
                payment3.getAmount(),
                payment3.getPayer(),
                payment3.getReceiver());

        //when
        LinkedList<PaymentDTO> payments = this.paymentService.selectPaymentsByRoom("ab036123-3b36-11eb-adc1-0242ac120003");


        //then
        Assertions.assertEquals(payments.size(), 2);
        Assertions.assertTrue(payments.get(0).equals(payment2));
        Assertions.assertTrue(payments.get(1).equals(payment3));
        Assertions.assertFalse(payment1.equals(payments.get(0)) && payment1.equals(payments.get(1)));
        cleanUp();
    }

    @Test
    void shouldSelectPaymentByRoomAndUser() throws BackendException {
        //given
        PaymentDTO payment1 = new PaymentDTO(
                "5fa6666a-37bd-11eb-adc1-0242ac120012",
                10.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment2 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment3 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        this.paymentService.insertPayment(
                payment1.getPaymentId(),
                payment1.getRoomId(),
                payment1.getAmount(),
                payment1.getPayer(),
                payment1.getReceiver());
        this.paymentService.insertPayment(
                payment2.getPaymentId(),
                payment2.getRoomId(),
                payment2.getAmount(),
                payment2.getPayer(),
                payment2.getReceiver());
        this.paymentService.insertPayment(
                payment3.getPaymentId(),
                payment3.getRoomId(),
                payment3.getAmount(),
                payment3.getPayer(),
                payment3.getReceiver());

        //when
        LinkedList<PaymentDTO> payments = this.paymentService.selectPaymentsByRoomAndUser("ab036123-3b36-11eb-adc1-0242ac120003", "ab036999-3b36-11eb-adc1-0242ac120321");

        //then
        Assertions.assertEquals(payments.size(), 2);
        Assertions.assertTrue(payments.get(0).equals(payment2));
        Assertions.assertTrue(payments.get(1).equals(payment3));
        cleanUp();
    }

    @Test
    void shouldSelectByRoomUserAndId() throws BackendException {
        //given
        PaymentDTO payment1 = new PaymentDTO(
                "5fa6666a-37bd-11eb-adc1-0242ac120012",
                10.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment2 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment3 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        this.paymentService.insertPayment(
                payment1.getPaymentId(),
                payment1.getRoomId(),
                payment1.getAmount(),
                payment1.getPayer(),
                payment1.getReceiver());
        this.paymentService.insertPayment(
                payment2.getPaymentId(),
                payment2.getRoomId(),
                payment2.getAmount(),
                payment2.getPayer(),
                payment2.getReceiver());
        this.paymentService.insertPayment(
                payment3.getPaymentId(),
                payment3.getRoomId(),
                payment3.getAmount(),
                payment3.getPayer(),
                payment3.getReceiver());

        // when
        PaymentDTO payment = this.paymentService.selectPaymentByRoomAndUserAndPaymentId(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                "ab036999-3b36-11eb-adc1-0242ac120321",
                payment3.getPaymentId()
        );
        //then
        Assertions.assertTrue(payment.equals(payment3));
    }

    @Test
    void shouldDeleteByRoom() throws BackendException {
        //given
        PaymentDTO payment1 = new PaymentDTO(
                "5fa6666a-37bd-11eb-adc1-0242ac120012",
                10.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment2 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment3 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        this.paymentService.insertPayment(
                payment1.getPaymentId(),
                payment1.getRoomId(),
                payment1.getAmount(),
                payment1.getPayer(),
                payment1.getReceiver());
        this.paymentService.insertPayment(
                payment2.getPaymentId(),
                payment2.getRoomId(),
                payment2.getAmount(),
                payment2.getPayer(),
                payment2.getReceiver());
        this.paymentService.insertPayment(
                payment3.getPaymentId(),
                payment3.getRoomId(),
                payment3.getAmount(),
                payment3.getPayer(),
                payment3.getReceiver());

        //when
        this.paymentService.deletePaymentsByRoom("ab036123-3b36-11eb-adc1-0242ac120003");

        //then
        LinkedList<PaymentDTO> payments = this.paymentService.selectAllPayments();
        Assertions.assertEquals(1,payments.size());
        cleanUp();
    }

    @Test
    void shouldDeleteByRoomAndUser() throws BackendException {
        //given
        PaymentDTO payment1 = new PaymentDTO(
                "5fa6666a-37bd-11eb-adc1-0242ac120003",
                10.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment2 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120300",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment3 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment4 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120301",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        this.paymentService.insertPayment(

                payment1.getPaymentId(),
                payment1.getRoomId(),
                payment1.getAmount(),
                payment1.getPayer(),
                payment1.getReceiver());
        this.paymentService.insertPayment(
                payment2.getPaymentId(),
                payment2.getRoomId(),
                payment2.getAmount(),
                payment2.getPayer(),
                payment2.getReceiver());
        this.paymentService.insertPayment(
                payment3.getPaymentId(),
                payment3.getRoomId(),
                payment3.getAmount(),
                payment3.getPayer(),
                payment3.getReceiver());
        this.paymentService.insertPayment(
                payment4.getPaymentId(),
                payment4.getRoomId(),
                payment4.getAmount(),
                payment4.getPayer(),
                payment4.getReceiver());
        //when
        this.paymentService.deletePaymentsByUser("ab036123-3b36-11eb-adc1-0242ac120003","ab036999-3b36-11eb-adc1-0242ac120321");

        //then
        LinkedList<PaymentDTO> payments = this.paymentService.selectAllPayments();
        Assertions.assertEquals(3,payments.size());
        cleanUp();
    }


    @Test
    void shouldDeleteById() throws BackendException {
        //given
        PaymentDTO payment1 = new PaymentDTO(
                "5fa6666a-37bd-11eb-adc1-0242ac120003",
                10.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment2 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120300",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment3 = new PaymentDTO(
                "ab036123-3b36-11eb-adc1-0242ac120003",
                15.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        PaymentDTO payment4 = new PaymentDTO(
                "5fa6666a-37bd-11eb-adc1-0242ac120003",
                10.0,
                "ab036999-3b36-11eb-adc1-0242ac120321",
                "ab036100-3b36-11eb-adc1-0242ac120005");
        this.paymentService.insertPayment(
                payment1.getPaymentId(),
                payment1.getRoomId(),
                payment1.getAmount(),
                payment1.getPayer(),
                payment1.getReceiver());
        this.paymentService.insertPayment(
                payment2.getPaymentId(),
                payment2.getRoomId(),
                payment2.getAmount(),
                payment2.getPayer(),
                payment2.getReceiver());
        this.paymentService.insertPayment(
                payment3.getPaymentId(),
                payment3.getRoomId(),
                payment3.getAmount(),
                payment3.getPayer(),
                payment3.getReceiver());
        this.paymentService.insertPayment(
                payment4.getPaymentId(),
                payment4.getRoomId(),
                payment4.getAmount(),
                payment4.getPayer(),
                payment4.getReceiver());

        //when
        this.paymentService.deletePaymentById("5fa6666a-37bd-11eb-adc1-0242ac120003","ab036999-3b36-11eb-adc1-0242ac120321",payment1.getPaymentId());

        //then
        LinkedList<PaymentDTO> result = this.paymentService.selectAllPayments();
        Assertions.assertEquals(3,result.size());
        cleanUp();


    }
    @After
    public void cleanUp() throws BackendException {
        LinkedList<PaymentDTO> result = this.paymentService.selectAllPayments();
        while (!result.isEmpty()) {
            this.paymentService.deletePaymentById(result.get(0).getRoomId(), result.get(0).getPayer(), result.get(0).getPaymentId());
            result = this.paymentService.selectAllPayments();
        }
        result = this.paymentService.selectAllPayments();
        assert (result.isEmpty());
    }
}