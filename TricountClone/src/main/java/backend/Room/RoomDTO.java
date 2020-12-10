package backend.Room;

public class RoomDTO {
    private String roomId;
    private String name;

    public RoomDTO(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public String getRoomId(){
        return roomId;
    }

    public String getName(){
        return name;
    }

    public void setRoomId(String roomId){
        this.roomId = roomId;
    }

    public void setName(String name){
        this.name = name;
    }
}
