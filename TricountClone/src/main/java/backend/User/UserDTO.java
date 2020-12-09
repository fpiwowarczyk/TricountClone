package backend.User;

public class UserDTO {

        public String name;
        public String password;
        public String userId;
        public String roomId;

        public UserDTO(String name,String password,String userId,String roomId){
            this.name = name;
            this.password = password;
            this.userId = userId;
            this.roomId = roomId;
        }

        public String getName(){
            return name;
        }
        public String getPassword(){
            return password;
        }
        public String getUserId(){
            return userId;
        }
        public String getRoomId(){
            return roomId;
        }
}
