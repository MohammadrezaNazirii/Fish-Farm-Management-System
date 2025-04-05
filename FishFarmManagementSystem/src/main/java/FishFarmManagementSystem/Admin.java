package FishFarmManagementSystem;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Admin extends User {
    public Admin(String name, String username, String password) {
        super(name, username, password);
    }

    public void createFarm() {
        // Implementation for creating farms
    }

    public void managePools() {
        // Implementation for managing pools
    }

    public void manageReports() {
        // Implementation for managing reports
    }



    @Override
    public void performDuties() {
        System.out.println("Managing farms, pools, and reports.");
    }


}
