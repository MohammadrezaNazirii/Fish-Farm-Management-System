package FishFarmManagementSystem;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class SuperAdmin extends User {
    public SuperAdmin(String name, String username, String password) {
        super(name, username, password);
    }

    public void manageAdmins() {
        // Implementation for managing admins
    }

    public void manageUsers() {
        // Implementation for managing users
    }

    @Override
    public void performDuties() {
        System.out.println("Managing admins and users.");
    }
}
