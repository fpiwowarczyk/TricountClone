package front;

import java.util.LinkedList;

public class User {

    private String name;
    private String password;
    private String userId;
    private LinkedList<String> rooms;

    public User(String name, String password, String userId, LinkedList<String> rooms) {
        this.name = name;
        this.password = password;
        this.userId = userId;
        this.rooms = rooms;
    }

    public User(String name, String password, String userId) {
        this.name = name;
        this.password = password;
        this.userId = userId;
        this.rooms = new LinkedList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserId(){
        return this.userId;
    }

    public LinkedList<String> getRooms(){
        return this.rooms;
    }

    public void setName(String name) { this.name = name;}

    public void setPassword(String password){this.password = password;}

    public void setUserId(String userId){ this.userId = userId;}

    public void setRooms(LinkedList<String> rooms){ this.rooms = rooms;}

    public void addRoom(String room){ this.rooms.add(room);}
}
