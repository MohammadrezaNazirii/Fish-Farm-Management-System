package FishFarmManagementSystem;


import FishFarmManagementSystem.service.FeedingReportService;
import FishFarmManagementSystem.service.UserService;
import FishFarmManagementSystem.ui.MainUI;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userID")
    @SequenceGenerator(name = "userID", sequenceName = "userID", allocationSize = 1)
    private int id;

    private String name;
    private String username;
    private String password;
    private String role;
    private Boolean blocked;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public static Boolean Login(String user, String pass){
        UserService userService = MainUI.context.getBean(UserService.class);
        User thisUser = userService.findUserByUsername(user);
        if(thisUser != null){
            return Objects.equals(thisUser.password, pass) && !thisUser.getBlocked();
        }else {
            return false;
        }
    }

    public static Integer addUser(String name, String username, String password, Integer role){
        UserService userService = MainUI.context.getBean(UserService.class);
        if(role == 1) {
            userService.createFeedingSpecialist(name, username, password);
        }else if(role == 2) {
            userService.createFacilitySpecialist(name, username, password);
        } else {
            userService.createAdmin(name, username, password);
        }
        return 1;
    }

    public static Integer deleteUser(int id) throws ChangeSetPersister.NotFoundException {
        UserService userService = MainUI.context.getBean(UserService.class);

        userService.deleteUserById(id);

        return 1;
    }

    public static Integer updateUser(int id, String name, String username, String password, String role, Boolean blocked){
        UserService userService = MainUI.context.getBean(UserService.class);

        userService.updateUser(id, name, username, password, role, blocked);

        return 1;
    }


    public abstract void performDuties();

    // Getters and Setters
    // Other common methods like login(), logout()

}
