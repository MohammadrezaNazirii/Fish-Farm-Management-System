package FishFarmManagementSystem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Feeder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feederID")
    @SequenceGenerator(name = "feederID", sequenceName = "feederID", allocationSize = 1)
    private Integer id;

    private String status; // e.g., "Operational", "Broken"
    private Integer lineId;

    // Constructor, Getters, and Setters
}
