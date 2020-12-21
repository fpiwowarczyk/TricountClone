package backend.User.Tests;

import backend.BackendException;
import backend.User.UserDTO;
import backend.User.UserService;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


//Tests should be played with empty database :)

import java.util.LinkedList;

class UserServiceTest {
    private final UserService userService;


    public UserServiceTest() throws BackendException {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        try {
            Session session = cluster.connect("tricount");
            this.userService = new UserService(session);
        } catch (Exception e) {
            throw new BackendException("Could not connect to the cluster. " + e.getMessage() + ".", e);
        }
        cleanUp();
    }

    @Test
    void shouldInsertAndSelectAllUsers() throws BackendException {
        //given
        UserDTO givenUser1 = new UserDTO(
                "Filip",
                "mojehaslo",
                "5fa3333a-37bd-11eb-adc1-0242ac120002");

        UserDTO givenUser2 = new UserDTO(
                "Krzysiek",
                "jegohaslo",
                "5fa6666a-37bd-11eb-adc1-0242ac120012");

        //when
        this.userService.insertUser(givenUser1.getName(),givenUser1.getPassword(),givenUser1.getRoomId());
        this.userService.insertUser(givenUser2.getName(),givenUser2.getPassword(),givenUser2.getRoomId());
        LinkedList<UserDTO> selectedUsers = this.userService.selectAllUsers();

        givenUser1.setUserId(selectedUsers.get(0).getUserId());
        givenUser2.setUserId(selectedUsers.get(1).getUserId());

        //then
            assert (selectedUsers.get(0).equals(givenUser1));
            assert (selectedUsers.get(1).equals(givenUser2));
            cleanUp();
    }

    @Test
    void shouldSelectUserByNameAndPassword() throws BackendException {
        //given
        UserDTO givenUser = new UserDTO(
                "Krzysiek",
                "jegohaslo",
                "5fa7928e-37bd-11eb-adc1-0242ac120123");
        UserDTO givenUser1 = new UserDTO(
                "Krzysiek",
                "jegohaslo",
                "5fa7928e-37bd-11eb-adc1-0242ac120021");

        this.userService.insertUser(givenUser.getName(), givenUser.getPassword(), givenUser.getUserId());
        this.userService.insertUser(givenUser1.getName(), givenUser1.getPassword(), givenUser1.getUserId());

        // when
        LinkedList<UserDTO> user = this.userService.selectUserByNameAndPassword("Krzysiek", "jegohaslo");

        //then
            Assertions.assertEquals(user.get(1).getUserId(), user.get(0).getUserId());
            cleanUp();
    }

    boolean containsUser(UserDTO user,LinkedList<UserDTO> listOfUsers){

        for(UserDTO userDto : listOfUsers){
            if(user.equals(userDto)){
                return true;
            }
        }
        return false;
    }

    @After
    public void cleanUp() throws BackendException {
        LinkedList<UserDTO> result = this.userService.selectAllUsers();
        while (!result.isEmpty()){
            this.userService.deleteUser(result.get(0).getName(),result.get(0).getPassword(),result.get(0).getUserId());
            result = this.userService.selectAllUsers();
        }
        result = this.userService.selectAllUsers();
        assert(result.isEmpty());
    }
}