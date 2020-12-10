package backend.Payment;

public class PaymentDTO {
    private String paymentId;
    private String roomId;
    private Double amount;
    private String payer;
    private String receiver;

    public PaymentDTO (String paymentId, String roomId, Double amount, String payer, String receiver){
        this.paymentId = paymentId;
        this.roomId = roomId;
        this.amount = amount;
        this.payer = payer;
        this.receiver = receiver;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getRoomId(){
        return  roomId;
    }

    public Double getAmount(){
        return  amount;
    }

    public String getPayer() {
        return payer;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
