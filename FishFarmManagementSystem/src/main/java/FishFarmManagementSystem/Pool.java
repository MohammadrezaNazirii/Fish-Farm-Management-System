package FishFarmManagementSystem;

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
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poolID")
    @SequenceGenerator(name = "poolID", sequenceName = "poolID", allocationSize = 1)
    private int id;

    private String otherEquipments;
    private double size;
    private Integer farmId;
//    private List<Line> lines;
//    private EquipmentSpecialist equipmentSpecialist;


    public Pool(String otherEquipments, double size) {
        this.otherEquipments = otherEquipments;
        this.size = size;
    }

    public static Integer addPool(String otherEquipments, double size){
        PoolService poolService = MainUI.context.getBean(PoolService.class);

        poolService.createPool(new Pool(otherEquipments, size));

        return 1;
    }

    public static Integer deletePool(int id) throws ChangeSetPersister.NotFoundException {
        PoolService poolService = MainUI.context.getBean(PoolService.class);
        LineService lineService = MainUI.context.getBean(LineService.class);

        List<Line> lines = lineService.getAllLinesByPoolId(id);

        for (Line line : lines) {
            lineService.updatePoolId(line.getId(), null);
        }

        poolService.deletePool(id);

        return 1;
    }

    public static Integer updatePool(int id, String otherEquipments, double size){
        PoolService poolService = MainUI.context.getBean(PoolService.class);

        Pool temp = new Pool(otherEquipments, size);

        poolService.updatePool(id, temp);

        return 1;
    }

    // Constructor, Getters, and Setters
}
