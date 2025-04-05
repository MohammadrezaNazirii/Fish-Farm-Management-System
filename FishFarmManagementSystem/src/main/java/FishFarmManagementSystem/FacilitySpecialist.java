package FishFarmManagementSystem;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class FacilitySpecialist extends User {
    public FacilitySpecialist(String name, String username, String password) {
        super(name, username, password);
    }

    public void calculateFeedingParameters() {
        // Implementation for feeding calculations
    }

    public void submitFeedingReport() {
        // Implementation for submitting feeding report
    }

//    public static FeedingSpecialist getTemporaryUser() {
//        return new FeedingSpecialist(1, "Temporary User", "feeding", "123");
//    }

    @Override
    public void performDuties() {
        System.out.println("Calculating feeding parameters and submitting reports.");
    }
}