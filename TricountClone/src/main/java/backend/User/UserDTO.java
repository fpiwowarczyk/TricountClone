package backend.User;

public class UserDTO {

    private String name;
    private String password;
    private String userId;
    private String roomId;

    public UserDTO(String name, String password, String userId, String roomId) {
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
}
