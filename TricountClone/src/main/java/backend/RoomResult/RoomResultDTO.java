package backend.RoomResult;

public class RoomResultDTO {
    private String roomId;
    private String userId;
    private Double money;

    public RoomResultDTO(String roomId,String userId,Double money){
        this.roomId = roomId;
        this.userId = userId;
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
    public void setRoomId(String roomId){
        this.roomId = roomId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setMoney(Double money){
        this.money = money;
    }
}
