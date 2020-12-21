package backend.Room;

import java.util.UUID;

public class RoomDTO {
    private String name;
    private String roomId;

    public RoomDTO(String name) {
        this.name = name;
        this.roomId = UUID.randomUUID().toString();
    }

    public RoomDTO(String name, String roomId) {
        this.name = name;
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(RoomDTO room) {
        return this.name.equals(room.getName()) &&
                this.roomId.equals(room.getRoomId());
    }
}
