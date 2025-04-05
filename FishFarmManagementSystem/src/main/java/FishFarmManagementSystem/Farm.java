package FishFarmManagementSystem;

import FishFarmManagementSystem.service.FarmService;
import FishFarmManagementSystem.service.LineService;
import FishFarmManagementSystem.service.PoolService;
import FishFarmManagementSystem.ui.MainUI;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmID")
    @SequenceGenerator(name = "farmID", sequenceName = "farmID", allocationSize = 1)
    private Integer id;


    private String name;
//    private List<Pool> pools;
    private Integer feedingSpecialistId;
//    private FeedingSpecialist feedingSpecialist;


    public Farm(String name, Integer feedingSpecialistId) {
        this.name = name;
        this.feedingSpecialistId = feedingSpecialistId;
    }

    public static Integer addFarm(String name, Integer feedingSpecialistId){
        FarmService farmService = MainUI.context.getBean(FarmService.class);

        farmService.createFarm(new Farm(name, feedingSpecialistId));

        return 1;
    }

    public static Integer deleteFarm(int id) throws ChangeSetPersister.NotFoundException {/// //////
        FarmService farmService = MainUI.context.getBean(FarmService.class);
        PoolService poolService = MainUI.context.getBean(PoolService.class);

        List<Pool> pools = poolService.getAllPoolsByFarmId(id);

        for (Pool pool : pools) {
            poolService.updateFarmID(pool.getId(), null);
        }

        farmService.deleteFarm(id);

        return 1;
    }
//
    public static Integer updateFarm(Integer id, String name, Integer feedingSpecialistId){
        FarmService farmService = MainUI.context.getBean(FarmService.class);

        Farm temp = new Farm(name, feedingSpecialistId);

        farmService.updateFarm(id, temp);

        return 1;
    }

    // Constructor, Getters, and Setters

}
