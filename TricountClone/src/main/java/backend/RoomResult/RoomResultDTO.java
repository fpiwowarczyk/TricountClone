package backend.RoomResult;

public class RoomResultDTO {
    private String roomId;
    private String userId;
    private String userName;
    private Double money;

    public RoomResultDTO(String roomId,String userId,String userName,Double money){
        this.roomId = roomId;
        this.userId = userId;
        this.userName = userName;
        this.money = money;
    }
    public String getRoomId() {
        return roomId;
    }
    public String getUserId() {
        return userId;
    }
    public Double getMoney(){
        return money;
    }
    public String getUserName() { return userName;}
    public void setRoomId(String roomId){
        this.roomId = roomId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setUserName(String userName) {this.userName = userName;}
    public void setMoney(Double money){
        this.money = money;
    }

    public String toString(){
        return "RoomId:"+ this.roomId+"\nUserId:"+this.userId+"\nUserName:"+this.userName+"\nMoney:"+this.money;
    }
}
