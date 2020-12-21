package backend.User;

import java.util.UUID;

public class UserDTO {

    private String name;
    private String password;
    private String userId;
    private String roomId;

    public UserDTO(String name, String password, String roomId) {
        this.name = name;
        this.password = password;
        this.userId = UUID.randomUUID().toString();
        this.roomId = roomId;
    }

    public UserDTO(String name, String password,String userId, String roomId) {
        this.name = name;
        this.password = password;
        this.userId = userId;
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean equals(UserDTO user) {
        return this.name.equals(user.name) &&
                this.password.equals(user.password) &&
                this.userId.equals(user.userId) &&
                this.roomId.equals(user.roomId);
    }
}
